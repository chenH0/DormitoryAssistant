<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys"
        :openKeys="['batch', 'base', 'business', 'member']"
        mode="inline"
        :style="{ height: '100%', borderRight: 0 }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <coffee-outlined /> &nbsp; 欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">
          <user-outlined /> &nbsp; 关于
        </router-link>
      </a-menu-item>
<!--      <a-sub-menu key="member">
        <template #title>
          <span>
            <UnorderedListOutlined />
            宿舍分区管理
          </span>
        </template>
        <a-menu-item key="/member/ticket">
          <router-link to="/member/ticket">
            <MenuUnfoldOutlined /> 会员车票
          </router-link>
        </a-menu-item>
      </a-sub-menu>-->
      <a-sub-menu key="business">
        <template #title>
          <span>
            <UnorderedListOutlined />
            业务管理
          </span>
        </template>
        <a-menu-item key="/business/daily-room">
          <router-link to="/business/daily-room">
            <MenuUnfoldOutlined /> &nbsp; 开放宿舍
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/daily-bed">
          <router-link to="/business/daily-bed">
            <MenuUnfoldOutlined /> &nbsp; 剩余床位
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/daily-student">
          <router-link to="/business/daily-student">
            <MenuUnfoldOutlined /> &nbsp; 学生资格
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/sk-token">
          <router-link to="/business/sk-token">
            <MenuUnfoldOutlined /> &nbsp; 令牌余量
          </router-link>
        </a-menu-item>
        <a-menu-item key="/business/confirm-order">
          <router-link to="/business/confirm-order">
            <MenuUnfoldOutlined /> &nbsp; 订单信息
          </router-link>
        </a-menu-item>
<!--        <a-menu-item key="/business/daily-train">
          <router-link to="/business/daily-train">
            <MenuUnfoldOutlined /> &nbsp; 每日楼栋
          </router-link>
        </a-menu-item>-->
      </a-sub-menu>
      <a-sub-menu key="base">
        <template #title>
          <span>
            <UnorderedListOutlined />
            基础数据
          </span>
        </template>
        <a-menu-item key="/base/station">
          <router-link to="/base/station">
            <MenuUnfoldOutlined /> &nbsp; 楼号管理
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train">
          <router-link to="/base/train">
            <MenuUnfoldOutlined /> &nbsp; 楼层管理
          </router-link>
        </a-menu-item>
<!--        <a-menu-item key="/base/train-station">
          <router-link to="/base/train-station">
            <MenuUnfoldOutlined /> &nbsp; 火车车站
          </router-link>
        </a-menu-item>-->
        <a-menu-item key="/base/train-carriage">
          <router-link to="/base/train-carriage">
            <MenuUnfoldOutlined /> &nbsp; 房间管理
          </router-link>
        </a-menu-item>
        <a-menu-item key="/base/train-seat">
          <router-link to="/base/train-seat">
            <MenuUnfoldOutlined /> &nbsp; 房间床位
          </router-link>
        </a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="batch">
        <template #title>
          <span>
            <UnorderedListOutlined />
            跑批管理
          </span>
        </template>
        <a-menu-item key="/batch/job">
          <router-link to="/batch/job">
            <MenuUnfoldOutlined /> &nbsp; 任务管理
          </router-link>
        </a-menu-item>
      </a-sub-menu>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {defineComponent, ref, watch} from 'vue';
import router from "@/router";

export default defineComponent({
  name: "the-sider-view",
  setup() {
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      selectedKeys
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
