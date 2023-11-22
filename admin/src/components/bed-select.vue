<template>
  <a-select v-model:value="index" show-search allowClear
            :filterOption="filterNameOption"
            @change="onChange" placeholder="请选择床位编号"
            :style="'width: ' + localWidth">
    <a-select-option v-for="item in BED_TYPE" :key="item.code" :value="item.code">
      {{item.desc}}
    </a-select-option>
  </a-select>
</template>

<script>

import {defineComponent, onMounted, ref, watch} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "bed-select-view",
  props: ["modelValue", "width"],
  emits: ['update:modelValue', 'change'],
  setup(props, {emit}) {
    const BED_TYPE = window.BED_TYPE;
    const index = ref();
    const stations = ref([]);
    const localWidth = ref(props.width);
    if (Tool.isEmpty(props.width)) {
      localWidth.value = "100%";
    }

    // 利用watch，动态获取父组件的值，如果放在onMounted或其它方法里，则只有第一次有效
    watch(() => props.modelValue, ()=>{
      console.log("props.modelValue", props.modelValue);
      index.value = props.modelValue;
    }, {immediate: true});

    /**
     * 查询所有的车站，用于车站下拉框
     */
    const queryAllStation = () => {
      axios.get("/business/admin/parti/query-all").then((response) => {
        let data = response.data;
        if (data.success) {
          stations.value = data.content;
        } else {
          notification.error({description: data.message});
        }
      });
    };

    /**
     * 车站下拉框筛选
     */
    const filterNameOption = (input, option) => {
      console.log(input, option);
      return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    };

    /**
     * 将当前组件的值响应给父组件
     * @param value
     */
    const onChange = (value) => {
      emit('update:modelValue', value);
      let station = stations.value.filter(item => item.code === value)[0];
      if (Tool.isEmpty(station)) {
        station = {};
      }
      emit('change', station);
    };

    onMounted(() => {
      queryAllStation();
    });

    return {
      BED_TYPE,
      index,
      stations,
      filterNameOption,
      onChange,
      localWidth
    };
  },
});
</script>
