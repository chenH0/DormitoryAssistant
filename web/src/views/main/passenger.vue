<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd(passengers)">修改</a-button>
    </a-space>
  </p>
  <a-table :dataSource="passengers"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
        <a-space>
          <a-popconfirm
              title="删除后不可恢复，确认删除?"
              @confirm="onDelete(record)"
              ok-text="确认" cancel-text="取消">
            <a style="color: red">删除</a>
          </a-popconfirm>
          <a @click="onAdd(passenger)">编辑</a>
        </a-space>
      </template>
      <template v-else-if="column.dataIndex === 'type'">
        <span v-for="item in PASSENGER_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.type">
            {{item.desc}}
          </span>
        </span>
      </template>
      <template v-else-if="column.dataIndex === 'status'">
        <span v-for="item in STATUS_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.status && record.status===1" style="color: deepskyblue">
            {{item.desc}}
          </span>
          <span v-else-if="item.code === record.status && record.status===0" style="color: red">
            {{item.desc}}
          </span>
        </span>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="个人信息修改" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="passenger" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="姓名">
        <a-input v-model:value="passenger.name" />
      </a-form-item>
      <a-form-item label="学号">
        <a-input v-model:value="passenger.idCard" />
      </a-form-item>
      <a-form-item label="培养类型">
        <a-select v-model:value="passenger.type">
          <a-select-option v-for="item in PASSENGER_TYPE_ARRAY" :key="item.code" :value="item.code">
            {{item.desc}}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="入学年份">
        <a-input v-model:value="passenger.year" />
      </a-form-item>
      <a-form-item label="学院">
        <a-input v-model:value="passenger.school" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "passenger-view",
  setup() {
    const PASSENGER_TYPE_ARRAY = window.PASSENGER_TYPE_ARRAY;
    const STATUS_TYPE_ARRAY = window.STATUS_TYPE_ARRAY;
    const visible = ref(false);
    let passenger = ref({
      memberId: undefined,
      name: undefined,
      idCard: undefined,
      type: undefined,
      year: undefined,
      school: undefined,
      room: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const passengers = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
      {
        title: 'ID',
        dataIndex: 'memberId',
        key: 'memberId',
      },
      {
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '学号',
        dataIndex: 'idCard',
        key: 'idCard',
      },
      {
        title: '培养类型',
        dataIndex: 'type',
        key: 'type',
      },
      {
        title: '年级',
        dataIndex: 'year',
        key: 'year',
      },
      {
        title: '学院',
        dataIndex: 'school',
        key: 'school',
      },
      {
        title: '分配宿舍',
        dataIndex: 'room',
        key: 'room',
      },
      {
        title: '最后修改时间',
        dataIndex: 'updateTime',
        key: 'updateTime',
      },
      {
        title: '审核状态',
        dataIndex: 'status',
        key: 'status',
      }
    ];

    const onAdd = () => {
      passenger.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      passenger.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/member/household/delete/" + record.id).then((response) => {
        const data = response.data;
        if (data.success) {
          notification.success({description: "删除成功！"});
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleOk = () => {
      axios.post("/member/household/save", passenger.value).then((response) => {
        let data = response.data;
        if (data.success) {
          notification.success({description: "保存成功！"});
          notification.success({description: "审核自动通过！"});
          visible.value = false;
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleQuery = (param) => {
      if (!param) {
        param = {
          page: 1,
          size: pagination.value.pageSize
        };
      }
      loading.value = true;
      axios.get("/member/household/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          passengers.value = data.content.list;
          // 设置分页控件的值
          pagination.value.current = param.page;
          pagination.value.total = data.content.total;
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleTableChange = (pagination) => {
      // console.log("看看自带的分页参数都有啥：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });

    return {
      PASSENGER_TYPE_ARRAY,
      STATUS_TYPE_ARRAY,
      passenger,
      visible,
      passengers,
      pagination,
      columns,
      handleTableChange,
      handleQuery,
      loading,
      onAdd,
      handleOk,
      onEdit,
      onDelete
    };
  },
});
</script>
