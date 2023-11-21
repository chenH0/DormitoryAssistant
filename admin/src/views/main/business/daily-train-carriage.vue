<template>
  <p>
    <a-space>
      <train-select-view v-model="params.floorCode" width="200px"></train-select-view>
      <a-button type="primary" @click="handleQuery()">查找</a-button>
      <a-button type="primary" @click="onAdd">禁用全部</a-button>
    </a-space>
  </p>
  <a-table :dataSource="trainCarriages"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column, record, checked }">
      <template v-if="column.dataIndex === 'status'">
        <span v-for="item in OPEN_TYPE" :key="item.code">
          <span v-if="item.code === record.status">
            <a-switch v-model:checked="record.status"
                      :checked-value="1"
                      :unchecked-value="0"
                      @change="handleOpen(record)">
              <template #checkedChildren><check-outlined /></template>
              <template #unCheckedChildren><close-outlined /></template>
            </a-switch>
          </span>
        </span>

      </template>
      <template v-else-if="column.dataIndex === 'bedType'">
        <span v-for="item in SEAT_TYPE_ARRAY" :key="item.code">
          <span v-if="item.code === record.bedType">
            {{item.desc}}
          </span>
        </span>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="房间设置" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="trainCarriage" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="楼层编号">
        <train-select-view v-model="trainCarriage.floorsCode"></train-select-view>
      </a-form-item>
      <a-form-item label="房间序号">
        <a-input v-model:value="trainCarriage.index" placeholder="请输入三位数门牌号"/>
      </a-form-item>
      <a-form-item label="房间类型">
        <a-select v-model:value="trainCarriage.bedType">
          <a-select-option v-for="item in SEAT_TYPE_ARRAY" :key="item.code" :value="item.code">
            {{item.desc}}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="床位数">
        <a-input v-model:value="trainCarriage.bedCount"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>


<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";
import TrainSelectView from "@/components/train-select";

export default defineComponent({
  name: "train-carriage-view",
  components: {TrainSelectView},
  setup() {
    const SEAT_TYPE_ARRAY = window.SEAT_TYPE_ARRAY;
    const OPEN_TYPE = window.OPEN_TYPE;
    const visible = ref(false);
    const checked = ref(0);
    let trainCarriage = ref({
      id: undefined,
      name: undefined,
      floorsCode: undefined,
      index: undefined,
      bedType: undefined,
      bedCount: undefined,
      createTime: undefined,
      updateTime: undefined,
      status: undefined,
    });
    const trainCarriages = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    let params = ref({
      floorCode: null
    });
    const columns = [
      {
        title: '房间名',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '房间类型',
        dataIndex: 'bedType',
        key: 'bedType',
      },
      {
        title: '床位数',
        dataIndex: 'bedCount',
        key: 'bedCount',
      },
      {
        title: '宿舍楼层编号',
        dataIndex: 'floorsCode',
        key: 'floorsCode',
      },
      {
        title: '宿舍编号',
        dataIndex: 'index',
        key: 'index',
      },
      {
        title: '开放选择',
        dataIndex: 'status'
      }
    ];

    const onAdd = () => {
      axios.post("/business/admin/room-carriage/closeAll").then((response) => {
        const data = response.data;
        if (data.success) {
          notification.success({description: "修改成功！"});
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const onEdit = (record) => {
      trainCarriage.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record ) => {
      axios.delete("/business/admin/room-carriage/delete/" + record.id).then((response) => {
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

    const handleOpen = (record) => {
      checked.value = 1-checked.value;
      record.status = checked.value;

      axios.post("/business/admin/room-carriage/updateStatus", {
        id: record.id,
        status: checked.value,
      }).then((response) => {
        const data = response.data;
        if (data.success) {
          //notification.success({description: "修改成功！"});
          //checked.value = record.status;
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleOk = () => {
      axios.post("/business/admin/room-carriage/save", trainCarriage.value).then((response) => {
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
      axios.get("/business/admin/room-carriage/query-list", {
        params: {
          page: param.page,
          size: param.size,
          floorCode: params.value.floorCode
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          trainCarriages.value = data.content.list;
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
      SEAT_TYPE_ARRAY,
      OPEN_TYPE,
      trainCarriage,
      visible,
      trainCarriages,
      pagination,
      columns,
      handleTableChange,
      handleOpen,
      handleQuery,
      loading,
      onAdd,
      handleOk,
      onEdit,
      onDelete,
      params
    };
  },
});
</script>
