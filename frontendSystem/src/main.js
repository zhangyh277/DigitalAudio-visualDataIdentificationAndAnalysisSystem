// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import axios from 'axios'
import upperFirst from 'lodash/upperFirst';
import camelCase from 'lodash/camelCase';
import App from './App'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import Contextmenu from "vue-contextmenujs"

Vue.config.productionTip = false;

Vue.use(Contextmenu);
Vue.use(Viewer);
Vue.use(ElementUI);

axios.defaults.baseURL = 'http://10.12.171.5:80/';
// axios.defaults.baseURL = 'http://localhost:8086/';
Vue.prototype.$axios = axios;


const requireComponent = require.context(
  // 其组件目录的相对路径
  './components',
  // 是否查询其子目录
  false,
  // 匹配基础组件文件名的正则表达式
  /[A-Z]\w+\.(vue|js)$/
);
requireComponent.keys().forEach(fileName => {
  // 获取组件配置
  const componentConfig = requireComponent(fileName);
  console.log('requireComponent', fileName);

  // 获取组件的 PascalCase 命名
  const componentName = upperFirst(
      camelCase(
          // 获取和目录深度无关的文件名
          fileName
              .split('/')
              .pop()
              .replace(/\.\w+$/, '')
      )
  );

  console.log('componentName', componentName);


  // mixLifecycle(componentConfig);

  // 全局注册组件
  Vue.component(
      componentName,
      // 如果这个组件选项是通过 `export default` 导出的，
      // 那么就会优先使用 `.default`，
      // 否则回退到使用模块的根。
      componentConfig.default || componentConfig
  );
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});



