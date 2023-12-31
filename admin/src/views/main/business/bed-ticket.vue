<template>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table :dataSource="bedTickets"
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
    </template>
  </a-table>
  <a-modal v-model:visible="visible" title="余票信息" @ok="handleOk"
           ok-text="确认" cancel-text="取消">
    <a-form :model="bedTicket" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="日期">
        <a-date-picker v-model:value="bedTicket.date" valueFormat="YYYY-MM-DD" placeholder="请选择日期" />
      </a-form-item>
      <a-form-item label="宿舍编号">
        <a-input v-model:value="bedTicket.roomCode" />
      </a-form-item>
      <a-form-item label="宿舍余票">
        <a-input v-model:value="bedTicket.total" />
      </a-form-item>
      <a-form-item label="左室余票">
        <a-input v-model:value="bedTicket.left" />
      </a-form-item>
      <a-form-item label="忠室余票">
        <a-input v-model:value="bedTicket.medium" />
      </a-form-item>
      <a-form-item label="右室余票">
        <a-input v-model:value="bedTicket.right" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";

export default defineComponent({
  name: "bed-ticket-view",
  setup() {
    const visible = ref(false);
    let bedTicket = ref({
      id: undefined,
      date: undefined,
      roomCode: undefined,
      total: undefined,
      left: undefined,
      medium: undefined,
      right: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const bedTickets = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    const columns = [
    {
      title: '日期',
      dataIndex: 'date',
      key: 'date',
    },
    {
      title: '宿舍编号',
      dataIndex: 'roomCode',
      key: 'roomCode',
    },
    {
      title: '宿舍余票',
      dataIndex: 'total',
      key: 'total',
    },
    {
      title: '左室余票',
      dataIndex: 'left',
      key: 'left',
    },
    {
      title: '忠室余票',
      dataIndex: 'medium',
      key: 'medium',
    },
    {
      title: '右室余票',
      dataIndex: 'right',
      key: 'right',
    },
    {
      title: '操作',
      dataIndex: 'operation'
    }
    ];

    const onAdd = () => {
      bedTicket.value = {};
      visible.value = true;
    };

    const onEdit = (record) => {
      bedTicket.value = window.Tool.copy(record);
      visible.value = true;
    };

    const onDelete = (record) => {
      axios.delete("/business/admin/bed-ticket/delete/" + record.id).then((response) => {
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
      axios.post("/business/admin/bed-ticket/save", bedTicket.value).then((response) => {
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
      axios.get("/business/admin/bed-ticket/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          bedTickets.value = data.content.list;
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
      bedTicket,
      visible,
      bedTickets,
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
