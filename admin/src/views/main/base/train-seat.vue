<template>
  <p>
    <a-space>
      <train-select-view v-model="params.floorsCode" width="200px"></train-select-view>
      <a-button type="primary" @click="handleQuery()">查找</a-button>
    </a-space>
  </p>
  <a-table :dataSource="trainSeats"
           :columns="columns"
           :pagination="pagination"
           @change="handleTableChange"
           :loading="loading">
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
      </template>
      <template v-else-if="column.dataIndex === 'col'">
        <span v-for="item in SEAT_COL_ARRAY" :key="item.code">
          <span v-if="item.code === record.col && item.type === record.seatType">
            {{item.desc}}
          </span>
        </span>
      </template>
      <template v-else-if="column.dataIndex === 'canSelect'">
        <span v-for="item in OPEN_TYPE" :key="item.code">
          <span v-if="item.code === record.canSelect">
            {{item.desc}}
          </span>
        </span>
      </template>
    </template>
  </a-table>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import {notification} from "ant-design-vue";
import axios from "axios";
import TrainSelectView from "@/components/train-select";

export default defineComponent({
  name: "train-seat-view",
  components: {TrainSelectView},
  setup() {
    const SEAT_COL_ARRAY = window.SEAT_COL_ARRAY;
    const OPEN_TYPE = window.OPEN_TYPE;
    const visible = ref(false);
    let trainSeat = ref({
      id: undefined,
      roomCode: undefined,
      floorsCode: undefined,
      roomName: undefined,
      index: undefined,
      userName: undefined,
      userId: undefined,
      status: undefined,
      canSelect: undefined,
      createTime: undefined,
      updateTime: undefined,
    });
    const trainSeats = ref([]);
    // 分页的三个属性名是固定的
    const pagination = ref({
      total: 0,
      current: 1,
      pageSize: 10,
    });
    let loading = ref(false);
    let params = ref({
      floorsCode: null
    });
    const columns = [
      {
        title: '宿舍编号',
        dataIndex: 'roomName',
        key: 'roomName',
      },
      {
        title: '楼层名',
        dataIndex: 'floorsCode',
        key: 'floorsCode',
      },
      {
        title: '床位号',
        dataIndex: 'index',
        key: 'index',
      },

      {
        title: '剩余床位',
        dataIndex: 'status',
        key: 'status',
      },
      {
        title: '开放选择',
        dataIndex: 'canSelect',
        key: 'canSelect',
      },
      {
        title: '住户名',
        dataIndex: 'userName',
        key: 'userName',
      },
    ];


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
          size: param.size,
          floorsCode: params.value.floorsCode
        }
      }).then((response) => {
        loading.value = false;
        let data = response.data;
        if (data.success) {
          trainSeats.value = data.content.list;
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
      SEAT_COL_ARRAY,
      OPEN_TYPE,
      trainSeat,
      visible,
      trainSeats,
      pagination,
      columns,
      handleTableChange,
      handleQuery,
      loading,
      params
    };
  },
});
</script>
