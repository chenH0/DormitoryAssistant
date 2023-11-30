<template>
  <!--  <p style="font-size: 20px; font-weight: bold">欢迎使用甲蛙12306售票系统</p>-->
  <div style="border: 4px solid orange;
              border-radius: 4px;
              padding: 10px; ">
    <h3>
      <b>宿管帮-演示网站</b>
    </h3>
    本项目分为<b>学生端</b>和<b>控台端</b>。<br>
    共有13张业务表，根据业务划分到四个数据库。<br>
    抢床位是本系统的核心功能。虽然抢床位流程看起来简单，但用了很多看不见的高并发技术。<br>
    比如功能需要满足几百个学生同时抢床位，由于每个人很难在第一次就抢选到心仪的床位，一分钟内请求可能突破十万条。<br>
    <li>利用<b>分布式缓存</b>，在抢床位开始前，提供高性能床位查询，同时要考虑缓存击穿、穿透、雪崩等问题</li>
    <li>使用<b>验证码</b>，纯前端验证码在前端削弱瞬时高峰，将100毫秒内几百人的请求，分散成1~2秒内几百人的请求</li>
<!--    <li>使用<b>第二层验证码</b>，后端验证码，进一步分散请求，同时防止机器人抢票</li>-->
    <li>使用<b>限流技术</b>减轻无谓请求，同时给用户快速失败响应（告知床位没有了）</li>
    <li>使用<b>令牌发放技术</b>，控制抢床位请求量，同时防止机器人刷票，比如开放300令牌。超出的请求快速失败。</li>
    <li>使用<b>分布式锁技术</b>，防止超卖，即500人抢500个床位，最终每个床位都只能卖出1个库存，不能同时被两个学生抢到</li>
    <li>使用<b>异步削峰+排队机制</b>，解决吞吐量问题，实现最短时间内给用户反馈</li>
    <li>使用<b>分布式事务</b>，保证数据最终一致性，不能库存减少了，学生和床位的绑定关系没有写入数据库。</li>
  </div>
  <a-divider style="border-color: #7cb305" dashed />
  <div style="border: 4px solid #99CCFF;
              border-radius: 4px;
              padding: 10px; ">
    <h3>
      <b>你现在访问的是学生端功能，控台端请点击
        <router-link to="/admin">
          控台管理
        </router-link></b>
    </h3>
    <div>
      如果你是新注册用户，需要先去
      <router-link to="/passenger">
        信息确认
      </router-link>，只有在控台端审批通过的学生才可以抢床位。
    </div>
    <div>
      抢床位功能支持选座，去
      <router-link to="/ticket">
        床位查询
      </router-link>
      。抢床位成功后，可以查看
      <router-link to="/my-ticket">
        我的床铺
      </router-link>
      ；去
      <router-link to="/seat">
        床位销售图
      </router-link>
      查看每个床位的占用情况
    </div>
    <div style="font-size: 14px; color: red">
      本项目还有一套控台管理模块，用于维护宿舍数据、调度任务，学生信息等功能，详情访问
      <a href="http://sgbbac.chenh.online" target="_blank">
        sgbbac.chenh.online
      </a>。
    </div>
    <div style="font-size: 14px; color: #888888">
      小提示：为了方便大家体验，本项目没对单人重复选座做校验，即：一个学生可多次选择床位。
    </div>
  </div>
</template>

<script>

import { defineComponent } from 'vue';

export default defineComponent({
  name: "welcome-view",
  setup() {
    return {
    };
  },
});
</script>
