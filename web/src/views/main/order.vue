<template>
<!--  <div>
  <p>
    <a-space>
      <a-button type="primary" @click="handleQuery()">回退</a-button>
      <a-button type="primary" @click="handleQuery()">刷新</a-button>
    </a-space>

  </p>
  </div>-->

  <div class="order-train">
    <span class="order-train-main">{{room.roomName}}</span>&nbsp;

    <span class="order-train-small">&ensp; 剩余 </span>
    <span class="order-train-big" style="color: dodgerblue">{{room.total}}</span>
    <span class="order-train-small"> 个空床位</span>
  </div>
  <a-divider></a-divider>

  <div>
    <div :style="{ marginTop: '16px' }">
      <b>勾选要选择的床位：</b>
      <a-radio-group v-model:value="valueChecks" button-style="solid">
        <a-radio-button value="1" v-if="room.one === 1" :disabled=false>1号床</a-radio-button>
        <a-radio-button value="1" v-if="room.one === 0" :disabled=true>1号床</a-radio-button>
        <a-radio-button value="2" v-if="room.two === 1" :disabled=false>2号床</a-radio-button>
        <a-radio-button value="2" v-if="room.two === 0" :disabled=true>2号床</a-radio-button>
        <a-radio-button value="3" v-if="room.three === 1" :disabled=false>3号床</a-radio-button>
        <a-radio-button value="3" v-if="room.three === 0" :disabled=true>3号床</a-radio-button>
        <a-radio-button value="4" v-if="room.four === 1" :disabled=false>4号床</a-radio-button>
        <a-radio-button value="4" v-if="room.four === 0" :disabled=true>4号床</a-radio-button>
      </a-radio-group>
    </div>
  </div>
  <a-checkbox-group v-model:value="roomChecks" :options="roomOptions" />

  <div class="order-tickets"  v-if="valueChecks > 0">
    <a-row class="order-tickets-header" v-if="tickets.length > 0">
      <a-col :span="4">姓名</a-col>
      <a-col :span="6">学号</a-col>
      <a-col :span="10">宿舍房间号</a-col>
      <a-col :span="2">床位类型</a-col>
    </a-row>
    <a-row class="order-tickets-row" v-for="ticket in tickets" :key="ticket.passengerId">
      <a-col :span="4">{{user.name}}</a-col>
      <a-col :span="6">{{user.idCard}}</a-col>
      <a-col :span="10">{{room.roomName}}</a-col>
      <a-col :span="2" v-if="valueChecks>0" style="color: dodgerblue">{{valueChecks}}号床</a-col>
    </a-row>
  </div>
  <div v-if="valueChecks > 0">
    <a-button type="primary" size="large" @click="finishCheckPassenger" style="float: right">提交订单</a-button>
  </div>

  <a-modal v-model:visible="visible" title="请核对床位信息"
           style="top: 80px; width: 650px"
           ok-text="确认" cancel-text="取消"
           @ok="showFirstImageCodeModal">
    <div class="order-tickets">
      <a-row class="order-tickets-header" v-if="tickets.length > 0">
        <a-col :span="3">姓名</a-col>
        <a-col :span="6">学号</a-col>
        <a-col :span="8">宿舍房间号</a-col>
        <a-col :span="4">床位类型</a-col>
      </a-row>
      <a-row class="order-tickets-row" v-for="ticket in tickets" :key="ticket.passengerId">
        <a-col :span="3">{{user.name}}</a-col>
        <a-col :span="6">{{user.idCard}}</a-col>
        <a-col :span="8">{{room.roomName}}</a-col>
        <a-col :span="4" v-if="valueChecks>0" style="color: dodgerblue">{{valueChecks}}号床</a-col>
      </a-row>
      <br/>
      <div v-if="chooseSeatType === 0" style="color: red;">
        点击确认后，请勿刷新页面，以免导致占床失败
      </div>

      <div v-if="chooseSeatType === 0" style="color: dodgerblue;">
        <div>宿管帮规则1：床位一经确认，不再支持更改</div>
        <div>宿管帮规则2：一名新生仅允许选择一个床位</div>
      </div>

<!--      <div v-else style="text-align: center">
        <a-switch class="choose-seat-item" v-for="item in SEAT_COL_ARRAY" :key="item.code"
                  v-model:checked="chooseSeatObj[item.code + '1']" :checked-children="item.desc" :un-checked-children="item.desc" />
        <div v-if="tickets.length > 1">
          <a-switch class="choose-seat-item" v-for="item in SEAT_COL_ARRAY" :key="item.code"
                    v-model:checked="chooseSeatObj[item.code + '2']" :checked-children="item.desc" :un-checked-children="item.desc" />
        </div>
        <div style="color: #999999">提示：您可以选择{{tickets.length}}个座位</div>
      </div>
      <br>-->

      <!--<br/>-->
      <!--最终购票：{{tickets}}-->
      <!--最终选座：{{chooseSeatObj}}-->
    </div>
  </a-modal>

  <!-- 第二层验证码 后端 -->
<!--  <a-modal v-model:visible="imageCodeModalVisible" :title="null" :footer="null" :closable="false"
           style="top: 80px; width: 400px">
    <p style="text-align: center; font-weight: bold; font-size: 18px">
      确认你是人类<br/>
    </p>
    <p>
      <a-input v-model:value="imageCode" placeholder="图片验证码">
        <template #suffix>
          <img v-show="!!imageCodeSrc" :src="imageCodeSrc" alt="验证码" v-on:click="loadImageCode()"/>
        </template>
      </a-input>
    </p>
    <a-button type="danger" block @click="handleOk">输入验证码后开始购票</a-button>
  </a-modal>-->

  <!-- 第一层验证码 纯前端 -->
  <a-modal v-model:visible="firstImageCodeModalVisible" :title="null" :footer="null" :closable="false"
           style="top: 80px; width: 400px">
    <p style="text-align: center; font-weight: bold; font-size: 18px">
      确认你是人类<br/>
    </p>
    <p>
      <a-input v-model:value="firstImageCodeTarget" placeholder="验证码">
        <template #suffix>
          {{firstImageCodeSourceA}} + {{firstImageCodeSourceB}}
        </template>
      </a-input>
    </p>
    <a-button type="danger" block @click="handleOk">输入验证码后开始购票</a-button>
  </a-modal>

  <a-modal v-model:visible="lineModalVisible" title="排队购票" :footer="null" :maskClosable="false" :closable="false"
           style="top: 50px; width: 400px">
    <div class="book-line">
      <div v-show="confirmOrderLineCount < 0">
        <loading-outlined /> 系统正在处理中...
      </div>
<!--      <div v-show="confirmOrderLineCount >= 0">
        <loading-outlined /> 您前面还有{{confirmOrderLineCount}}排队中，请稍候
      </div>-->
    </div>
    <br/>
    <a-button type="danger" @click="onCancelOrder">取消购票</a-button>
  </a-modal>
</template>

<script>

import {defineComponent, ref, onMounted, watch, computed} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "order-view",
  setup() {
    const room = ref([]);
    const user = ref([]);
    const roomOptions = ref([]);
    const roomChecks = ref([]);
    const valueChecks = ref();
    const dailyTrainTicket = SessionStorage.get(SESSION_ORDER) || {};
    console.log("下单的车次信息", dailyTrainTicket);

    const SEAT_TYPE = window.SEAT_TYPE;
    console.log(SEAT_TYPE)

    const seatTypes = [];
    for (let KEY in SEAT_TYPE) {
      let key = KEY.toLowerCase();
      if (dailyTrainTicket[key] >= 0) {
        seatTypes.push({
          type: KEY,
          code: SEAT_TYPE[KEY]["code"],
          desc: SEAT_TYPE[KEY]["desc"],
          count: dailyTrainTicket[key],
          price: dailyTrainTicket[key + 'Price'],
        })
      }
    }
    console.log("本车次提供的座位：", seatTypes)

    const tickets = ref([]);
    const PASSENGER_TYPE_ARRAY = window.PASSENGER_TYPE_ARRAY;
    const visible = ref(false);
    const lineModalVisible = ref(false);
    const confirmOrderId = ref();
    const confirmOrderLineCount = ref(-1);
    const lineNumber = ref(5);

    // 勾选或去掉某个乘客时，在购票列表中加上或去掉一张表
    watch(() => roomChecks.value, (newVal, oldVal)=>{
      console.log("勾选乘客发生变化", newVal, oldVal)
      // 每次有变化时，把购票列表清空，重新构造列表
      tickets.value = [];
      roomChecks.value.forEach((item) => tickets.value.push({
        passengerId: item.id,
        passengerType: item.type,
        seatTypeCode: seatTypes[0].code,
        passengerName: item.name,
        passengerIdCard: item.idCard
      }))
    }, {immediate: true});

    watch(() => valueChecks.value, (newVal, oldVal)=>{
      console.log("勾选乘客发生变化", newVal, oldVal)
      // 每次有变化时，把购票列表清空，重新构造列表
      tickets.value = [];
      console.log(valueChecks.value);
      tickets.value.push({
        room:valueChecks.value,
        user:user.value
      })

    }, {immediate: true});

    // 0：不支持选座；1：选一等座；2：选二等座
    const chooseSeatType = ref(0);
    // 根据选择的座位类型，计算出对应的列，比如要选的是一等座，就筛选出ACDF，要选的是二等座，就筛选出ABCDF
    const SEAT_COL_ARRAY = computed(() => {
      return window.SEAT_COL_ARRAY.filter(item => item.type === chooseSeatType.value);
    });
    // 选择的座位
    // {
    //   A1: false, C1: true，D1: false, F1: false，
    //   A2: false, C2: false，D2: true, F2: false
    // }
    const chooseSeatObj = ref({});
    watch(() => SEAT_COL_ARRAY.value, () => {
      chooseSeatObj.value = {};
      for (let i = 1; i <= 2; i++) {
        SEAT_COL_ARRAY.value.forEach((item) => {
          chooseSeatObj.value[item.code + i] = false;
        })
      }
      console.log("初始化两排座位，都是未选中：", chooseSeatObj.value);
    }, {immediate: true});

    const handleQueryPassenger = () => {
      axios.get("/business/bed-ticket/query", {
        params: {
          id: dailyTrainTicket.id,
        }
      }).then((response) => {
        let data = response.data;
        if (data.success) {
          room.value = data.content;
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleQueryUser = () => {
      axios.get("/member/household/getUser").then((response) => {
        let data = response.data;
        if (data.success) {
          user.value = data.content;
          user.value.forEach((item) => passengerOptions.value.push({
            label: item.name,
            value: item
          }))
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const finishCheckPassenger = () => {
      if (valueChecks.value > 0) {
        console.log("hello")
        /*axios.get("/member/household/getUser").then((response) => {
          let data = response.data;
          if (data.success) {
            user.value = data.content;
            user.value.forEach((item) => passengerOptions.value.push({
              label: item.name,
              value: item
            }))
          } else {
            notification.error({description: data.message});
          }
        });*/
        visible.value = true;
      }

      // 校验余票是否充足，购票列表中的每个座位类型，都去车次座位余票信息中，看余票是否充足
      // 前端校验不一定准，但前端校验可以减轻后端很多压力
      // 注意：这段只是校验，必须copy出seatTypesTemp变量来扣减，用原始的seatTypes去扣减，会影响真实的库存
      let seatTypesTemp = Tool.copy(seatTypes);
      for (let i = 0; i < tickets.value.length; i++) {
        let ticket = tickets.value[i];
        for (let j = 0; j < seatTypesTemp.length; j++) {
          let seatType = seatTypesTemp[j];
          // 同类型座位余票-1，这里扣减的是临时copy出来的库存，不是真正的库存，只是为了校验
          if (ticket.seatTypeCode === seatType.code) {
            seatType.count--;
            if (seatType.count < 0) {
              notification.error({description: seatType.desc + '余票不足'});
              return;
            }
          }
        }
      }
      console.log("前端余票校验通过");

      // 判断是否支持选座，只有纯一等座和纯二等座支持选座
      // 先筛选出购票列表中的所有座位类型，比如四张表：[1, 1, 2, 2]
      let ticketSeatTypeCodes = [];
      for (let i = 0; i < tickets.value.length; i++) {
        let ticket = tickets.value[i];
        ticketSeatTypeCodes.push(ticket.seatTypeCode);
      }
      // 为购票列表中的所有座位类型去重：[1, 2]
      const ticketSeatTypeCodesSet = Array.from(new Set(ticketSeatTypeCodes));
      console.log("选好的座位类型：", ticketSeatTypeCodesSet);
      if (ticketSeatTypeCodesSet.length !== 1) {
        console.log("选了多种座位，不支持选座");
        chooseSeatType.value = 0;
      } else {
        // ticketSeatTypeCodesSet.length === 1，即只选择了一种座位（不是一个座位，是一种座位）
        if (ticketSeatTypeCodesSet[0] === SEAT_TYPE.YDZ.code) {
          console.log("一等座选座");
          chooseSeatType.value = SEAT_TYPE.YDZ.code;
        } else if (ticketSeatTypeCodesSet[0] === SEAT_TYPE.EDZ.code) {
          console.log("二等座选座");
          chooseSeatType.value = SEAT_TYPE.EDZ.code;
        } else {
          console.log("不是一等座或二等座，不支持选座");
          chooseSeatType.value = 0;
        }

        // 余票小于20张时，不允许选座，否则选座成功率不高，影响出票
        if (chooseSeatType.value !== 0) {
          for (let i = 0; i < seatTypes.length; i++) {
            let seatType = seatTypes[i];
            // 找到同类型座位
            if (ticketSeatTypeCodesSet[0] === seatType.code) {
              // 判断余票，小于20张就不支持选座
              if (seatType.count < 20) {
                console.log("余票小于20张就不支持选座")
                chooseSeatType.value = 0;
                break;
              }
            }
          }
        }
      }

      // 弹出确认界面
      visible.value = true;

    };

    const handleOk = () => {
      /*if (Tool.isEmpty(imageCode.value)) {
        notification.error({description: '验证码不能为空'});
        return;
      }*/
      lineModalVisible.value = true;
      console.log("选好的座位：", chooseSeatObj.value);

      // 设置每张票的座位
      // 先清空购票列表的座位，有可能之前选了并设置座位了，但选座数不对被拦截了，又重新选一遍
      for (let i = 0; i < tickets.value.length; i++) {
        tickets.value[i].seat = null;
      }
      let i = -1;
      // 要么不选座位，要么所选座位应该等于购票数，即i === (tickets.value.length - 1)
      for (let key in chooseSeatObj.value) {
        if (chooseSeatObj.value[key]) {
          i++;
          if (i > tickets.value.length - 1) {
            notification.error({description: '所选座位数大于购票数'});
            return;
          }
          tickets.value[i].seat = key;
        }
      }
      if (i > -1 && i < (tickets.value.length - 1)) {
        notification.error({description: '所选座位数小于购票数'});
        return;
      }

      console.log("最终购票：", tickets.value);

      axios.post("/business/confirm-order/do", {
        roomName: room.value.roomName,
        floorsCode: room.value.floorsCode,
        memberName: user.value.name,
        index: valueChecks.value,
        dateRoomTicketId: room.value.id
      }).then((response) => {
        let data = response.data;
        if (data.success) {
          notification.success({description: "下单成功！"});
          visible.value = false;
          imageCodeModalVisible.value = false;
          clearInterval(queryLineCountInterval);
          lineModalVisible.value = false;
          confirmOrderId.value = data.content;
          setTimeout(queryLineCount, 500);
          queryLineCount();
        } else {
          notification.error({description: data.message});
        }
      });
    }

    /* ------------------- 定时查询订单状态 --------------------- */
    // 确认订单后定时查询
    let queryLineCountInterval;

    // 定时查询订单结果/排队数量
    const queryLineCount = () => {
      window.location.href="/my-ticket"
      /*confirmOrderLineCount.value = -1;
      queryLineCountInterval = setInterval(function () {
        axios.get("/business/confirm-order/query-line-count/" + confirmOrderId.value).then((response) => {
          let data = response.data;
          if (data.success) {
            let result = data.content;
            switch (result) {
              case -1 :
                notification.success({description: "购票成功！"});
                lineModalVisible.value = false;
                clearInterval(queryLineCountInterval);
                break;
              case -2:
                notification.error({description: "购票失败！"});
                lineModalVisible.value = false;
                clearInterval(queryLineCountInterval);
                break;
              case -3:
                notification.error({description: "抱歉，没票了！"});
                lineModalVisible.value = false;
                clearInterval(queryLineCountInterval);
                break;
              default:
                confirmOrderLineCount.value = result;
            }
          } else {
            notification.error({description: data.message});
          }
        });
      }, 500);*/
    };



    /* ------------------- 第二层验证码 --------------------- */
    const imageCodeModalVisible = ref();
    const imageCodeToken = ref();
    const imageCodeSrc = ref();
    const imageCode = ref();
    /**
     * 加载图形验证码
     */
    const loadImageCode = () => {
      imageCodeToken.value = Tool.uuid(8);
      imageCodeSrc.value = process.env.VUE_APP_SERVER + '/business/kaptcha/image-code/' + imageCodeToken.value;
    };

    const showImageCodeModal = () => {
      loadImageCode();
      imageCodeModalVisible.value = true;
    };

    /* ------------------- 第一层验证码 --------------------- */
    const firstImageCodeSourceA = ref();
    const firstImageCodeSourceB = ref();
    const firstImageCodeTarget = ref();
    const firstImageCodeModalVisible = ref();

    /**
     * 加载第一层验证码
     */
    const loadFirstImageCode = () => {
      // 获取1~10的数：Math.floor(Math.random()*10 + 1)
      firstImageCodeSourceA.value = Math.floor(Math.random()*10 + 1) + 10;
      firstImageCodeSourceB.value = Math.floor(Math.random()*10 + 1) + 20;
    };

    /**
     * 显示第一层验证码弹出框
     */
    const showFirstImageCodeModal = () => {
      loadFirstImageCode();
      firstImageCodeModalVisible.value = true;
    };

    /**
     * 校验第一层验证码
     */
    const validFirstImageCode = () => {
      if (parseInt(firstImageCodeTarget.value) === parseInt(firstImageCodeSourceA.value + firstImageCodeSourceB.value)) {
        // 第一层验证通过
        firstImageCodeModalVisible.value = false;
        showImageCodeModal();
      } else {
        notification.error({description: '验证码错误'});
      }
    };

    /**
     * 取消排队
     */
    const onCancelOrder = () => {
      axios.get("/business/confirm-order/cancel/" + confirmOrderId.value).then((response) => {
        let data = response.data;
        if (data.success) {
          let result = data.content;
          if (result === 1) {
            notification.success({description: "取消成功！"});
            // 取消成功时，不用再轮询排队结果
            clearInterval(queryLineCountInterval);
            lineModalVisible.value = false;
          } else {
            notification.error({description: "取消失败！"});
          }
        } else {
          notification.error({description: data.message});
        }
      });
    };

    onMounted(() => {
      handleQueryPassenger();
      handleQueryUser();
    });

    return {
      room,
      user,
      dailyTrainTicket,
      seatTypes,
      roomOptions,
      roomChecks,
      valueChecks,
      tickets,
      PASSENGER_TYPE_ARRAY,
      visible,
      finishCheckPassenger,
      chooseSeatType,
      chooseSeatObj,
      SEAT_COL_ARRAY,
      handleOk,
      imageCodeToken,
      imageCodeSrc,
      imageCode,
      showImageCodeModal,
      imageCodeModalVisible,
      loadImageCode,
      firstImageCodeSourceA,
      firstImageCodeSourceB,
      firstImageCodeTarget,
      firstImageCodeModalVisible,
      showFirstImageCodeModal,
      validFirstImageCode,
      lineModalVisible,
      confirmOrderId,
      confirmOrderLineCount,
      onCancelOrder,
      lineNumber
    };
  },
});
</script>

<style>
.order-train .order-train-main {
  font-size: 22px;
  font-weight: bold;
}
.order-train .order-train-small {
  font-size: 14px;
}
.order-train .order-train-big {
  font-size: 20px;
  font-weight: bold;
}
.order-train .order-train-ticket {
  margin-top: 18px;
}
.order-train .order-train-ticket .order-train-ticket-main {
  color: red;
  font-size: 18px;
}

.order-tickets {
  margin: 10px 0;
}
.order-tickets .ant-col {
  padding: 5px 10px;
}
.order-tickets .order-tickets-header {
  background-color: cornflowerblue;
  border: solid 1px cornflowerblue;
  color: white;
  font-size: 16px;
  padding: 5px 0;
}
.order-tickets .order-tickets-row {
  border: solid 1px cornflowerblue;
  border-top: none;
  vertical-align: middle;
  line-height: 30px;
}

.order-tickets .choose-seat-item {
  margin: 5px 5px;
}
</style>
<script setup>
</script>