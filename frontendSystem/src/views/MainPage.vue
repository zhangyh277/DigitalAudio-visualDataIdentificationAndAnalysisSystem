<template>
  <el-container id="MainPage">
    <el-header style="border-style:none none solid none; border-color:#48517c; border-width:thin;height: 50px; margin-top: 0; margin-bottom: 0; margin-outside: 0">
      <system-title/>
    </el-header>
    <el-main style="margin-top: fill; margin-bottom: fill; margin-outside: 0">
      <el-container style="margin-top: fill; margin-bottom: fill;  margin-outside: 0">
        <el-aside style="border-style:solid; border-color:#48517c; border-width:thin; width:300px">
          <el-main style="height: 50%; border-style:solid; border-color:#48517c; border-width:thin;">
            <el-container>
              <el-header style="background-color:#4b5589; height: 50px; border-style:solid; border-color:#48517c; border-width:thin;">
                <span style="color:white">资源管理</span>
                <el-upload
                  multiple
                  class="upload-demo inline-block"
                  ref="upload"
                  action=""
                  :file-list="fileList"
                  :on-change="handleChange"
                  :on-success="handleSuccess"
                  :before-upload="beforeUpload"
                  :show-file-list="false"
                  :auto-upload="false"
                >
                  <el-button slot="trigger" size="small" type="primary">选择</el-button>
                  <!--<el-button size="small" type="primary" @click="submitUpload">上传</el-button>-->
                </el-upload>
              </el-header>
              <el-main>
                <el-collapse v-model="activeName">
                  <el-collapse-item name="1">
                    <template slot="title">
                      图片({{imageFileList.length}})
                    </template>
                    <ul>
                      <!--eslint-disable-next-line-->
                      <li v-for="file in imageFileList">
                        <span style="color:white">{{file.name}}</span>
                        <img :src="url.createObjectURL(file.raw)" class="avatar" @contextmenu.prevent="onContextmenu(file,$event)"
                             @click.stop.prevent="selectFile(file)">
                        <!--<button @click="handleRemove(file)">删除</button>-->
                      </li>
                    </ul>
                  </el-collapse-item>
                  <el-collapse-item name="2">
                    <template slot="title">
                      音频({{audioFileList.length}})
                    </template>
                    <ul>
                      <!--eslint-disable-next-line-->
                      <li v-for="file in audioFileList">
                        <button @click.stop.prevent="selectFile(file)" @contextmenu.prevent="onContextmenu(file,$event)">{{file.name}}</button>
                        <!--<button @click="handleRemove(file)">删除</button>-->
                      </li>
                    </ul>
                  </el-collapse-item>
                  <el-collapse-item name="3" title="视频">
                    <template slot="title">
                      视频({{videoFileList.length}})
                    </template>
                    <ul>
                      <!--eslint-disable-next-line-->
                      <li v-for="file in videoFileList">
                        <span style="color:white">{{file.name}}</span>
                        <video ref="video" :src="url.createObjectURL(file.raw)" class="avatar" @click.stop.prevent="selectFile(file)" @contextmenu.prevent="onContextmenu(file,$event)"/>
                        <!--<button @click="handleRemove(file)">删除</button>-->
                      </li>
                    </ul>
                  </el-collapse-item>
                </el-collapse>
              </el-main>
            </el-container>
          </el-main>
          <el-main style="height: 50%; border-style:solid; border-color:#48517c; border-width:thin;">
            <el-container>
              <el-header style="background-color:#4b5589; height: 50px; border-style:solid; border-color:#48517c; border-width:thin;">
                <span style="color:white">文件信息</span>
              </el-header>
              <el-main style="color:white; line-height: 25px; float: left">
                <div v-if="this.selectedFile!=null" style="float: left">
                  <div>名称：{{this.selectedFile.name}}</div>
                  <div>类型：{{this.selectedFile.raw.type}}</div>
                  <div>
                    <span>文件大小：</span>
                    <span v-if="this.selectedFile.size / 1024 / 1024 < 1">{{(this.selectedFile.size / 1024).toFixed(2) + 'KB'}}</span>
                    <span v-else>{{(this.selectedFile.size / 1024 / 1024).toFixed(2) + 'MB'}}</span>
                  </div>
                  <div>最后修改时间：{{dateFormat(new Date(this.selectedFile.raw.lastModified))}}</div>
                </div>
              </el-main>
            </el-container>
          </el-main>

        </el-aside>

        <el-main style="border-style:solid; border-color:#48517c; border-width:thin;">
          <div v-if="selectedResultFile!=null">
            <!--dialog 显示结果文件-->
            <div v-if="selectedResultFile.features[0] != null">
              <viewer>
                <img :src="selectedResultFile.features[0].fileSrc" class="avatar">
              </viewer>
            </div>
          </div>
          <div v-else-if="selectedFile!=null">
            <div v-if="selectedFile.raw.type == 'image/jpeg' || selectedFile.raw.type == 'image/png'">
              <viewer>
                <img :src="url.createObjectURL(selectedFile.raw)" class="avatar">
              </viewer>
            </div>
            <div v-else-if="selectedFile.raw.type == 'audio/mp3' || selectedFile.raw.type == 'audio/wav'" >
              <div id="waveForm" ref="waveform_Ref"></div>
              <div style="padding: 30px">
                <el-button
                  type="primary"
                  size="small"
                  icon="el-icon-video-play"
                  @click="playMusic"
                  v-if="!playing"
                >
                  播放
                </el-button>
                <el-button
                  v-if="playing"
                  type="primary"
                  size="small"
                  icon="el-icon-video-pause"
                  @click="playMusic"
                >
                  暂停
                </el-button>
              </div>
              <!--<audio ref="audio" controls="" :src="url.createObjectURL(selectedFile.raw)" />-->
            </div>
            <div v-else-if="selectedFile.raw.type == 'video/mp4'">
              <video ref="video" controls="" :src="url.createObjectURL(selectedFile.raw)" class="avatar"/>
            </div>
          </div>
        </el-main>

        <el-aside style="border-style:solid; border-color:#48517c; border-width:thin; width:300px">
          <el-main style="height: 50%; border-style:solid; border-color:#48517c; border-width:thin;">
            <el-container>
              <el-header style="background-color:#4b5589; height: 50px; border-style:solid; border-color:#48517c; border-width:thin; color:white">算法选择列表</el-header>
              <!--根据点击的文件类型，选择性显示算法列表-->
              <el-main style="line-height: 25px; float: left; color:white;">
                <div v-if="selectedFile!=null">
                  <div v-if="selectedFile.raw.type == 'image/jpeg' || selectedFile.raw.type == 'image/png'">
                    <button @click="handleImageMethod(selectedFile,'imageDetection')" style="float: left">
                      <i class="el-icon-edit">imageDetection</i>
                    </button>
                    <button @click="handleImageMethod(selectedFile,'imageForenSic')" style="float: left">
                      <i class="el-icon-edit">imageForenSic</i>
                    </button>
                  </div>
                  <div v-else-if="selectedFile.raw.type == 'audio/mp3' || selectedFile.raw.type == 'audio/wav'">
                    <button @click="handleAudioMethod(selectedFile,'audioRepeat')" style="float: left">
                      <i class="el-icon-edit">audioRepeat</i>
                    </button>
                    <button @click="handleAudioMethod(selectedFile,'audioResample')" style="float: left">
                      <i class="el-icon-edit">audioResample</i>
                    </button>
                    <button @click="handleAudioMethod(selectedFile,'audioSplice')" style="float: left">
                      <i class="el-icon-edit">audioSplice</i>
                    </button>
                  </div>
                  <div v-else-if="selectedFile.raw.type == 'video/mp4'">
                    <button @click="handleVideoMethod(selectedFile,'videoMethod1')" style="float: left">
                      <i class="el-icon-edit">videoMethod1</i>
                    </button>
                  </div>
                </div>
              </el-main>
            </el-container>
          </el-main>
          <el-main style="height: 50%; border-style:solid; border-color:#48517c; border-width:thin;">
            <el-container>
              <el-header style="background-color:#4b5589; height: 50px; border-style:solid; border-color:#48517c; border-width:thin; color:white">任务与结果</el-header>
              <el-main style="line-height: 25px; float: left">
                <!--显示算法返回结果-->
                <ul>
                  <!--eslint-disable-next-line-->
                  <li v-for="result in resultList">
                    <!--TODO:根据status更改颜色-->
                    <button :style="handleResultStyle(result)" @dblclick="selectResultFile(result)">
                      <div v-if="result!=null">{{result.conclusion}}</div>
                      <div e-else>算法调用失败</div>
                    </button>
                  </li>
                </ul>
              </el-main>
            </el-container>
          </el-main>
        </el-aside>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
  import WaveSurfer from "wavesurfer.js";

  export default {
    name: 'MainPage',
    data() {
      return {
        selectedFile: null,
        selectedResultFile: null,
        url: URL,
        imageFileList: [],
        audioFileList: [],
        videoFileList: [],
        resultList:[],
        fileList: [],
        activeName: '1',
        // date: new Date(),
        wavesurfer: null,
        playing: false,
      }
    },
    mounted() {
      // document.querySelector('body').setAttribute('style','background-color:#161e44');
      //获取当前日期
    },
    beforeDestroy: function () {
      // document.querySelector('body').removeAttribute('style');
      //TODO：删除File的URL URL.revokeObjectURL()
    },
    methods: {
      handleResultStyle(result){
        if(result.succeed == "true"){
          if(result.status == "true")return {backgroundColor:"blue"};
          else return {backgroundColor:"green"};
        }
        else return {backgroundColor:"red"};
      },
      selectResultFile(result){
        if(result.features && result.features.length && result.features[0].fileSrc){
          this.selectedResultFile = result;
        }
      },
      playMusic() {
        this.wavesurfer.playPause.bind(this.wavesurfer)();
        this.playing = !this.playing;
      },
      submitUpload() {
        this.$refs.upload.submit();
      },
      submitSingleFile(file){
        let formData = new FormData();
        formData.append("file",file.raw);
        var config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        this.$axios.post('/upload', formData, config)
          .then(function (res) {
            console.log(res);
          })
          .catch(function (error) {// 请求失败处理
            console.log(error);
          });
      },
      handleChange(file, fileList) {//文件数量改变
        console.log("arrive handleChange");
        this.fileList = fileList;
        this.imageFileList = [];
        this.audioFileList = [];
        this.videoFileList = [];
        this.fileList.forEach(curFile => {
          if (curFile.raw.type == 'image/jpeg' || curFile.raw.type == 'image/png') {
            this.imageFileList.push(curFile);
          }
          else if (curFile.raw.type == 'audio/mp3' || curFile.raw.type == 'audio/wav') {
            this.audioFileList.push(curFile);
          }
          else if (curFile.raw.type == 'video/mp4') {
            this.videoFileList.push(curFile);
          }
        });
      },
      handleRemove(file) {
        this.fileList = this.fileList.filter((item) => item !== file);
        this.handleChange(null, this.fileList);
      },
      handleSuccess(res, file) {
        //根据上传的文件类型，分类
        console.log(res, file);
      },
      beforeUpload(file) {
        //TODO:限制上传的资源类型、资源容量
        return true;
      },
      selectFile(file) {
        this.selectedResultFile = null;
        this.selectedFile = file;
        if(file.raw.type == 'audio/mp3' || file.raw.type == 'audio/wav'){
          this.$nextTick(() => {
            if(this.wavesurfer){
              this.wavesurfer.destroy();
            }
            console.log("到达nextTick");
            this.wavesurfer = WaveSurfer.create({
              // 波形图的容器
              container: this.$refs.waveform_Ref,
              // 已播放波形的颜色
              // progressColor: "red",
              // 未播放波形的颜色
              // waveColor: "lightgrey",
              // 波形图的高度，单位为px
              // height: 10,
              // 是否显示滚动条，默认为false
              // scrollParent: true,
              // 波形的振幅（高度），默认为1
              // barHeight: 0.8,
              // 波形条的圆角
              // barRadius: 2,
              // 波形条的宽度
              // barWidth: 1,
              // 波形条间的间距
              // barGap: 3
              // 播放进度光标条的颜色
              // cursorColor: "red",
              // 播放进度光标条的宽度，默认为1
              // cursorWidth: 10,
              // 播放进度颜色
              // progressColor: "blue",
              //  波形容器的背景颜色
              // backgroundColor: "yellow",
              // 音频的播放速度
              // audioRate: "1",
              // （与区域插件一起使用）启用所选区域的循环
              // loopSelection:false
            });
            this.wavesurfer.load(this.url.createObjectURL(file.raw));
          });
          if(this.wavesurfer){
            this.wavesurfer.destroy();
          }
        }

        console.log(this.selectedFile);
      },
      //当前日期格式化
      dateFormat(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1 < 10 ?
          '0' + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        // var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
        // var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
        // var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
        return year + '-' + month + '-' + day;
      },
      handleImageMethod(file, methodName) {
        console.log("arrived handleImageMethod");
        let formData = new FormData();
        formData.append("file",file.raw);
        var config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        this.$axios.post('/imageMethod/' + methodName, formData, config)
          .then(res => {
            if(res.data == null)console.error("返回值为空，后端错误");
            console.log(res.data);
            this.resultList.push(res.data);
          });
      },
      handleAudioMethod(file, methodName) {
        let formData = new FormData();
        formData.append("file",file.raw);
        var config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        //将后端找寻文件所需的文件名、需要调用的方法名作为参数传递给后端接口
        this.$axios.post('/audioMethod/' + methodName, formData, config)
          .then(res => {
            if(res.data == null)console.error("返回值为空，后端错误");
            console.log(res.data);
            this.resultList.push(res.data);
          });
        console.log("arrived");
      },
      handleVideoMethod(file, methodName) {
        let formData = new FormData();
        formData.append("file",file.raw);
        var config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        //将后端找寻文件所需的文件名、需要调用的方法名作为参数传递给后端接口
        this.$axios.post('/videoMethod/' + methodName, formData, config)
          .then(res => {
            if(res.data == null)console.error("返回值为空，后端错误");
            console.log(res.data);
            this.resultList.push(res.data);
          });
        console.log("arrived");
      },
      onContextmenu(file, event) {
        this.$contextmenu({
          items: [
            {
              label: "删除",
              onClick: ()=>{
                this.handleRemove(file)
              }
            },
          ],
          event, // 鼠标事件信息
          // x: event.x,
          // y: event.y,
          customClass: "custom-class", // 自定义菜单 class
          zIndex: 3, // 菜单样式 z-index
          minWidth: 230 // 主菜单最小宽度
        });
        return false;
      }
    }
  }
</script>

<style scoped>
  #MainPage {
    height:100%;
    width:100%;
    background-color:#161e44;
  }

  .el-container {
    height: 100%;
    color: #180953;
  }

  .el-header {
    color: #290a53;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
    text-align: center;
  }

  .el-main {
    color: #333;
    text-align: center;
  }

  /*设定图片样式*/
  .avatar {
    width: 100%;
    height: 100%;
    display: block;
  }

  .inline-block {
    display: inline-block;
  }

  p {
    margin: 0;
    padding: 0;
  }

  .custom-class .menu_item__available:hover,

  .custom-class .menu_item_expand {
    background: #ffecf2 !important;
    color: #ff4050 !important;
  }

  .el-collapse{
    border:none
  }

  /deep/ .el-collapse-item__header{
    border: none;
    background-color:#161e44;
    color: white;
  }

  /deep/ .el-collapse-item__wrap{
    border: none;
    background-color:#161e44;
    color: white;
  }

  ul {
    list-style-type: none;
    padding: 0;
    margin: 0;
  }

</style>
