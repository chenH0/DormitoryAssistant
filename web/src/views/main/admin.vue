<template>
  <div style="border: 4px solid #99CCFF;
              border-radius: 4px;
              padding: 10px; ">
    <h3>
      <b>控台端请点击
        <a href="http://sgbbac.chenh.online" target="_blank">
          sgbbac.chenh.online
        </a></b>
    </h3>
    <div style="font-size: 14px; color: red">
      小提示：为了方便大家体验，本项目不对控台进行身份校验。
    </div>
    <div>
      控台总体分成四大部分：
      <ul>
        <li>基础数据：用于维护所有的宿舍基础数据，包括宿舍楼、楼层、房间、床位等</li>
        <li>业务数据：用于维护每天的宿舍信息及床位信息，设置下单令牌，查看订单等</li>
        <li>学生管理：用于管理学生、住户、已绑定的床位等</li>
        <li>跑批管理：用于管理系统所有的跑批信息，包括跑批的新增、删除、暂停、重启、重跑等</li>
      </ul>
    </div>
  </div>
</template>

<script>

import { defineComponent } from 'vue';

export default defineComponent({
  name: "admin-view",
  setup() {
    return {
    };
  },
});
</script>
