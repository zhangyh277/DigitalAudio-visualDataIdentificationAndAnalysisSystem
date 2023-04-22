<template>
  <header>
    <div style="width: 200px;">xxxxxx平台</div>
    <div style="flex:1">数字声像鉴别系统</div>
    <div class="opt-wrapper">
      <el-dropdown :hide-on-click="false">
        <div class="demo-basic--circle">
          <div class="block">
            <el-avatar :size="30" :src="avatar" :class="['avatar-info','el-icon-s-tools']">
            </el-avatar>
          </div>
        </div>
        <el-dropdown-menu slot="dropdown" style="margin-top:-30px;margin-left: -40px;">
          <el-dropdown-item><i class="el-icon-info"></i>item1</el-dropdown-item>
          <el-dropdown-item><i class="el-icon-switch-button"></i>item2</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="opt-wrapper">
      <el-dropdown :hide-on-click="false">
        <div class="demo-basic--circle">
          <div class="block">
            <el-avatar :size="30" :src="avatar" :class="['avatar-info','el-icon-question']">
            </el-avatar>
          </div>
        </div>
        <el-dropdown-menu slot="dropdown" style="margin-top:-30px;margin-left: -40px;">
          <el-dropdown-item><i class="el-icon-info"></i>item1</el-dropdown-item>
          <el-dropdown-item><i class="el-icon-switch-button"></i>item2</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="opt-wrapper">
      <el-dropdown :hide-on-click="false">
        <div class="demo-basic--circle">
          <div class="block">
            <el-avatar :size="30" :src="avatar" :class="['avatar-info', 'el-icon-user']">
            </el-avatar>
          </div>
        </div>
        <el-dropdown-menu slot="dropdown" style="margin-top:-30px;margin-left: -40px;">
          <el-dropdown-item><i class="el-icon-info"></i>个人中心</el-dropdown-item>
          <el-dropdown-item><i class="el-icon-switch-button"></i>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </header>
</template>

<script>
export default {
    name: 'SystemTitle',
    data() {
        return {
            avatar:require('../assets/logo.png')
        };
    }
}
</script>

<style scoped>
    header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        color: #fff;
    }
    /*设定头像图片样式*/
    .avatar-info {
        margin-top: 10px;
        margin-right: 40px;
        cursor: pointer;
    }
</style>
