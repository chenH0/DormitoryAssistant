<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="bedSeats"
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
          <a @click="onEdit(record)">编辑</a>
        </a-space>
      </template>
      <template v-else-if="column.dataIndex === 'status'">
        <span v-for="item in 0未占用，1已占用_ARRAY" :key="item.code">
          <span v-if="item.code === record.status">
            {{item.desc}}
          </span>
        </span>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="座位" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="bedSeat" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="宿舍编号">
        <a-input v-model:value="bedSeat.roomCode" />
      </a-form-item>
      <a-form-item label="宿舍名">
        <a-input v-model:value="bedSeat.roomName" />
      </a-form-item>
      <a-form-item label="排号">
        <a-input v-model:value="bedSeat.index" />
      </a-form-item>
      <a-form-item label="状态">
        <a-select v-model:value="bedSeat.status">
          <a-select-option v-for="item in 0未占用，1已占用_ARRAY" :key="item.code" :value="item.code">
            {{item.desc}}
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "bed-seat-view",
  setup() {
    const 0未占用，1已占用_ARRAY = window.0未占用，1已占用_ARRAY;
    const visible = ref(false);
    let bedSeat = ref({
      id: undefined,
      roomCode: undefined,
      roomName: undefined,
      index: undefined,
      status: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const bedSeats = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    {
      title: '宿舍编号',
      dataIndex: 'roomCode',
      key: 'roomCode',
    },
    {
      title: '宿舍名',
      dataIndex: 'roomName',
      key: 'roomName',
    },
    {
      title: '排号',
      dataIndex: 'index',
      key: 'index',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      bedSeat.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      bedSeat.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/business/admin/bed-seat/delete/" + record.id).then((response) => {
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
      axios.post("/business/admin/bed-seat/save", bedSeat.value).then((response) => {
        let data = response.data;
        if (data.success) {
          notification.success({description: "保存成功！"});
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
      axios.get("/business/admin/bed-seat/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          bedSeats.value = data.content.list;
          // 设置分页控件的值
          pagination.value.current = param.page;
          pagination.value.total = data.content.total;
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleTableChange = (page) => {
      // console.log("看看自带的分页参数都有啥：" + JSON.stringify(page));
      pagination.value.pageSize = page.pageSize;
      handleQuery({
        page: page.current,
        size: page.pageSize
      });
    };

    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });

    return {
      0未占用，1已占用_ARRAY,
      bedSeat,
      visible,
      bedSeats,
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
