let baseUrl = "";
switch (process.env.NODE_ENV) {
  case 'dev':
    baseUrl = "http://localhost:8086/"  //开发环境url
    break
  case 'serve':
    baseUrl = "http://121.46.19.2:8086/"   //生产环境url
    break
}

export default baseUrl;
