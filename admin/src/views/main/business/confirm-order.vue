<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="confirmOrders"
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
        <span v-for="item in CONFIRM_ORDER_STATUS_ARRAY" :key="item.code">
          <span v-if="item.code === record.status">
            {{item.desc}}
          </span>
        </span>
      </template>
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="确认订单" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="confirmOrder" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="学生id">
        <a-input v-model:value="confirmOrder.memberId" />
      </a-form-item>
      <a-form-item label="学生名字">
        <a-input v-model:value="confirmOrder.memberName" />
      </a-form-item>
      <a-form-item label="日期">
        <a-date-picker v-model:value="confirmOrder.date" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="宿舍名字">
        <a-input v-model:value="confirmOrder.roomName" />
      </a-form-item>
      <a-form-item label="宿舍楼层">
        <a-input v-model:value="confirmOrder.floorsCode" />
      </a-form-item>
      <a-form-item label="床位号">
        <a-input v-model:value="confirmOrder.index" />
      </a-form-item>
      <a-form-item label="宿舍ID">
        <a-input v-model:value="confirmOrder.dateRoomTicketId" />
      </a-form-item>
      <a-form-item label="订单状态">
        <a-select v-model:value="confirmOrder.status">
          <a-select-option v-for="item in CONFIRM_ORDER_STATUS_ARRAY" :key="item.code" :value="item.code">
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
  name: "confirm-order-view",
  setup() {
    const CONFIRM_ORDER_STATUS_ARRAY = window.CONFIRM_ORDER_STATUS_ARRAY;
    const visible = ref(false);
    let confirmOrder = ref({
      id: undefined,
      memberId: undefined,
      memberName: undefined,
      date: undefined,
      roomName: undefined,
      floorsCode: undefined,
      index: undefined,
      dateRoomTicketId: undefined,
      status: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const confirmOrders = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    {
      title: '学生id',
      dataIndex: 'memberId',
      key: 'memberId',
    },
    {
      title: '学生名字',
      dataIndex: 'memberName',
      key: 'memberName',
    },
    {
      title: '日期',
      dataIndex: 'date',
      key: 'date',
    },
    {
      title: '宿舍名字',
      dataIndex: 'roomName',
      key: 'roomName',
    },
    {
      title: '宿舍楼层',
      dataIndex: 'floorsCode',
      key: 'floorsCode',
    },
    {
      title: '床位号',
      dataIndex: 'index',
      key: 'index',
    },
    {
      title: '宿舍ID',
      dataIndex: 'dateRoomTicketId',
      key: 'dateRoomTicketId',
    },
    {
      title: '订单状态',
      dataIndex: 'status',
      key: 'status',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      confirmOrder.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      confirmOrder.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/business/admin/confirm-order/delete/" + record.id).then((response) => {
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
      axios.post("/business/admin/confirm-order/save", confirmOrder.value).then((response) => {
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
      axios.get("/business/admin/confirm-order/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          confirmOrders.value = data.content.list;
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
      CONFIRM_ORDER_STATUS_ARRAY,
      confirmOrder,
      visible,
      confirmOrders,
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
