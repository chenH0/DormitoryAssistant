<template>
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
      <b>你现在访问的是控台端功能，学生端请点击
        <a href="http://sgb.chenh.online" target="_blank">
          sgb.chenh.online
        </a></b>
    </h3>
    <div style="font-size: 14px; color: red">
      小提示：为了方便大家体验，本项目不对控台进行身份校验。
    </div>
    <div style="font-size: 14px; color: #888888">
      本项目还有一套学生端系统，用于学生信息、抢床位，电器报修等功能。
    </div>
  </div>
</template>
<script>
import { defineComponent } from 'vue';

export default defineComponent({
  setup() {
    return {
    };
  },
});
</script>
<style>
</style>
