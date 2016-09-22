define("biz_common/dom/class.js",[],function(){
"use strict";
function s(s,a){
return s.classList?s.classList.contains(a):s.className.match(new RegExp("(\\s|^)"+a+"(\\s|$)"));
}
function a(s,a){
s.classList?s.classList.add(a):this.hasClass(s,a)||(s.className+=" "+a);
}
function e(a,e){
if(a.classList)a.classList.remove(e);else if(s(a,e)){
var c=new RegExp("(\\s|^)"+e+"(\\s|$)");
a.className=a.className.replace(c," ");
}
}
function c(c,l){
s(c,l)?e(c,l):a(c,l);
}
return{
hasClass:s,
addClass:a,
removeClass:e,
toggleClass:c
};
});define("appmsg/report_and_source.js",["biz_common/utils/string/html.js","biz_common/dom/event.js","biz_common/utils/url/parse.js","biz_wap/utils/ajax.js","biz_wap/jsapi/core.js"],function(e,t,o){
"use strict";
function i(){
var e=s.indexOf("://")<0?"http://"+s:s;
if(-1!=e.indexOf("mp.weixin.qq.com/s")||-1!=e.indexOf("mp.weixin.qq.com/mp/appmsg/show")){
var t=e.split("#");
e=r.addParam(t[0],"scene",25,!0)+(t[1]?"#"+t[1]:""),e=e.replace(/#rd$/g,"#wechat_redirect");
}else e="http://mp.weixinbridge.com/mp/wapredirect?url="+encodeURIComponent(s);
var o={
url:"/mp/advertisement_report"+location.search+"&report_type=3&action_type=0&url="+encodeURIComponent(s)+"&__biz="+biz+"&r="+Math.random(),
type:"GET",
mayAbort:!0,
async:!1
};
return o.timeout=2e3,o.complete=function(){
location.href=e;
},m(o),!1;
}
e("biz_common/utils/string/html.js");
var n=e("biz_common/dom/event.js"),r=e("biz_common/utils/url/parse.js"),m=e("biz_wap/utils/ajax.js"),c=msg_title.htmlDecode(),s=msg_source_url.htmlDecode(),a=document.getElementById("js_report_article3"),p=e("biz_wap/jsapi/core.js");
n.tap(a,function(){
var e=["/mp/infringement?url=",encodeURIComponent(location.href),"&title=",encodeURIComponent(c),"&__biz=",biz].join("");
return location.href=e+"#wechat_redirect",!1;
});
var l=document.getElementById("js_view_source");
n.on(l,"click",function(){
return i(),!1;
});
});define("appmsg/page_pos.js",["biz_common/utils/string/html.js","biz_common/dom/event.js","biz_wap/utils/ajax.js","biz_common/utils/cookie.js","appmsg/cdn_img_lib.js","biz_wap/utils/storage.js"],function(e){
"use strict";
function t(e){
for(var t=5381,o=0;o<e.length;o++)t=(t<<5)+t+e.charCodeAt(o),t&=2147483647;
return t;
}
function o(e,t){
if(e&&!(e.length<=0))for(var o,n,i,a=/http(s)?\:\/\/([^\/\?]*)(\?|\/)?/,d=0,r=e.length;r>d;++d)o=e[d],
o&&(n=o.getAttribute(t),n&&(i=n.match(a),i&&i[2]&&(g[i[2]]=!0)));
}
function n(e){
for(var t=0,o=f.length;o>t;++t)if(f[t]==e)return!0;
return!1;
}
function i(){
g={},o(document.getElementsByTagName("a"),"href"),o(document.getElementsByTagName("link"),"href"),
o(document.getElementsByTagName("iframe"),"src"),o(document.getElementsByTagName("script"),"src"),
o(document.getElementsByTagName("img"),"src");
var e=[];
for(var t in g)g.hasOwnProperty(t)&&(window.networkType&&"wifi"==window.networkType&&!_&&n(t)&&(_=!0),
e.push(t));
return g={},e.join(",");
}
function a(){
var e,t=window.pageYOffset||document.documentElement.scrollTop,o=document.getElementById("js_content"),n=document.documentElement.clientHeight||window.innerHeight,a=document.body.scrollHeight||document.body.offsetHeight,d=Math.ceil(a/n),s=Math.ceil((o.scrollHeight||o.offsetHeight)/n),l=(window.logs.read_height||t)+n,w=document.getElementById("js_toobar3").offsetTop,g=o.getElementsByTagName("img")||[],f=Math.ceil(l/n)||1,p=document.getElementById("media"),u=50,h=0,y=0,v=0,b=0,T=l+u>w?1:0;
f>d&&(f=d);
var j=function(t){
if(t)for(var o=0,n=t.length;n>o;++o){
var i=t[o];
if(i){
h++;
var a=i.getAttribute("src"),d=i.getAttribute("data-type");
a&&0==a.indexOf("http")&&(y++,a.isCDN()&&(v++,-1!=a.indexOf("tp=webp")&&b++),d&&(e["img_"+d+"_cnt"]=e["img_"+d+"_cnt"]||0,
e["img_"+d+"_cnt"]++));
}
}
e.download_cdn_webp_img_cnt=b||0,e.download_img_cnt=y||0,e.download_cdn_img_cnt=v||0,
e.img_cnt=h||0;
},O=window.appmsgstat||{},x=window.logs.img||{},z=window.logs.pagetime||{},E=x.load||{},k=x.read||{},D=[],B=[],N=0,S=0,I=0;
for(var H in k)H&&0==H.indexOf("http")&&k.hasOwnProperty(H)&&B.push(H);
for(var H in E)H&&0==H.indexOf("http")&&E.hasOwnProperty(H)&&D.push(H);
for(var M=0,P=D.length;P>M;++M){
var Y=D[M];
Y&&Y.isCDN()&&(-1!=Y.indexOf("/0")&&N++,-1!=Y.indexOf("/640")&&S++,-1!=Y.indexOf("/300")&&I++);
}
var e={
__biz:biz,
title:msg_title.htmlDecode(),
mid:mid,
idx:idx,
read_cnt:O.read_num||0,
like_cnt:O.like_num||0,
screen_height:n,
screen_num:s,
idkey:"",
copyright_stat:"",
ori_article_type:"",
video_cnt:window.logs.video_cnt||0,
read_screen_num:f||0,
is_finished_read:T,
scene:source,
content_len:c.content_length||0,
start_time:page_begintime,
end_time:(new Date).getTime(),
img_640_cnt:S,
img_0_cnt:N,
img_300_cnt:I,
wtime:z.wtime||0,
ftime:z.ftime||0,
ptime:z.ptime||0,
reward_heads_total:window.logs.reward_heads_total||0,
reward_heads_fail:window.logs.reward_heads_fail||0,
outer_pic:window.logs.outer_pic||0,
publish_time:publish_time.replace(/-/g,"")
};
if(window.networkType&&"wifi"==window.networkType&&(e.wifi_all_imgs_cnt=D.length,
e.wifi_read_imgs_cnt=B.length),window.logs.webplog&&4==window.logs.webplog.total){
var A=window.logs.webplog;
e.webp_total=1,e.webp_lossy=A.lossy,e.webp_lossless=A.lossless,e.webp_alpha=A.alpha,
e.webp_animation=A.animation;
}
if(e.copyright_stat=window._copyright_stat||"",e.ori_article_type=window._ori_article_type||"",
window.__addIdKeyReport&&window.moon&&(moon.hit_num>0&&window.__addIdKeyReport(27613,30,moon.hit_num),
moon.mod_num>0&&window.__addIdKeyReport(27613,31,moon.mod_num)),window.logs.idkeys){
var R=window.logs.idkeys,C=[];
for(var J in R)if(R.hasOwnProperty(J)){
var K=R[J];
K.val>0&&C.push(J+"_"+K.val);
}
e.idkey=C.join(";");
}
j(!!p&&p.getElementsByTagName("img")),j(g);
var q=(new Date).getDay(),L=i();
(_||0!==user_uin&&Math.floor(user_uin/100)%7==q)&&(e.domain_list=L),_&&(e.html_content=m),
window.isSg&&(e.from="sougou"),e.source=window.friend_read_source||"",e.recommend_version=window.friend_read_version||"",
e.class_id=window.friend_read_class_id||"",r({
url:"/mp/appmsgreport?action=page_time",
type:"POST",
mayAbort:!0,
data:e,
async:!1,
timeout:2e3
});
}
e("biz_common/utils/string/html.js");
{
var d=e("biz_common/dom/event.js"),r=e("biz_wap/utils/ajax.js");
e("biz_common/utils/cookie.js");
}
e("appmsg/cdn_img_lib.js");
var m,s=e("biz_wap/utils/storage.js"),l=new s("ad"),w=new s("page_pos"),c={};
!function(){
if(m=document.getElementsByTagName("html"),m&&1==!!m.length){
m=m[0].innerHTML;
var e=m.replace(/[\x00-\xff]/g,""),t=m.replace(/[^\x00-\xff]/g,"");
c.content_length=1*t.length+3*e.length+"<!DOCTYPE html><html></html>".length;
}
window.logs.pageinfo=c;
}();
var g={},_=!1,f=["wap.zjtoolbar.10086.cn","125.88.113.247","115.239.136.61","134.224.117.240","hm.baidu.com","c.cnzz.com","w.cnzz.com","124.232.136.164","img.100msh.net","10.233.12.76","wifi.witown.com","211.137.132.89","qiao.baidu.com","baike.baidu.com"],p=null,u=0,h=msg_link.split("?").pop(),y=t(h);
!function(){
if(window.localStorage&&!localStorage.getItem("clear_page_pos")){
for(var e=localStorage.length-1;e>=0;){
var t=localStorage.key(e);
t.match(/^\d+$/)?localStorage.removeItem(t):t.match(/^adinfo_/)&&localStorage.removeItem(t),
e--;
}
localStorage.setItem("clear_page_pos","true");
}
}(),window.localStorage&&(d.on(window,"load",function(){
u=1*w.get(y);
var e=location.href.indexOf("scrolltodown")>-1?!0:!1,t=(document.getElementById("img-content"),
document.getElementById("js_cmt_area"));
if(e&&t&&t.offsetTop){
var o=t.offsetTop;
window.scrollTo(0,o-25);
}else window.scrollTo(0,u);
}),d.on(window,"unload",function(){
if(w.set(o,u,+new Date+72e5),window.__ajaxtest="2",window._adRenderData&&"undefined"!=typeof JSON&&JSON.stringify){
var e=JSON.stringify(window._adRenderData),t=+new Date,o=[biz,sn,mid,idx].join("_");
l.set(o,{
info:e,
time:t
},+new Date+24e4);
}
a();
}),window.logs.read_height=0,d.on(window,"scroll",function(){
var e=window.pageYOffset||document.documentElement.scrollTop;
window.logs.read_height=Math.max(window.logs.read_height,e),clearTimeout(p),p=setTimeout(function(){
u=window.pageYOffset,w.set(y,u,+new Date+72e5);
},500);
}),d.on(document,"touchmove",function(){
var e=window.pageYOffset||document.documentElement.scrollTop;
window.logs.read_height=Math.max(window.logs.read_height,e),clearTimeout(p),p=setTimeout(function(){
u=window.pageYOffset,w.set(y,u,+new Date+72e5);
},500);
}));
});define("appmsg/cdn_speed_report.js",["biz_common/dom/event.js","biz_wap/jsapi/core.js","biz_wap/utils/ajax.js"],function(e){
"use strict";
function n(){
function e(e){
var n=[];
for(var i in e)n.push(i+"="+encodeURIComponent(e[i]||""));
return n.join("&");
}
if(networkType){
var n=window.performance||window.msPerformance||window.webkitPerformance;
if(n&&"undefined"!=typeof n.getEntries){
var i,t,o=100,a=document.getElementsByTagName("img"),s=a.length,p=navigator.userAgent,m=!1;
/micromessenger\/(\d+\.\d+)/i.test(p),t=RegExp.$1;
for(var g=0,w=a.length;w>g;g++)if(i=parseInt(100*Math.random()),!(i>o)){
var d=a[g].getAttribute("src");
if(d&&!(d.indexOf("mp.weixin.qq.com")>=0)){
for(var f,c=n.getEntries(),u=0;u<c.length;u++)if(f=c[u],f.name==d){
r({
type:"POST",
url:"/mp/appmsgpicreport?__biz="+biz+"#wechat_redirect",
data:e({
rnd:Math.random(),
uin:uin,
version:version,
client_version:t,
device:navigator.userAgent,
time_stamp:parseInt(+new Date/1e3),
url:d,
img_size:a[g].fileSize||0,
user_agent:navigator.userAgent,
net_type:networkType,
appmsg_id:window.appmsgid||"",
sample:s>100?100:s,
delay_time:parseInt(f.duration),
from:window.isSg?"sougou":""
})
}),m=!0;
break;
}
if(m)break;
}
}
}
}
}
var i=e("biz_common/dom/event.js"),t=e("biz_wap/jsapi/core.js"),r=e("biz_wap/utils/ajax.js"),o={
"network_type:fail":"fail",
"network_type:edge":"2g/3g",
"network_type:wwan":"2g/3g",
"network_type:wifi":"wifi"
};
t.invoke("getNetworkType",{},function(e){
networkType=o[e.err_msg],n();
}),i.on(window,"load",n,!1);
});define("appmsg/wxtopic.js",["biz_wap/utils/ajax.js","biz_wap/jsapi/core.js"],function(t){
"use strict";
function e(t){
t.parentNode.removeChild(t);
}
function i(t,e){
var i=document.getElementById("topic_tpl").innerHTML;
e.img_url||(e.img_url=topic_default_img);
for(var a in e){
var n=new RegExp("{"+a+"}","g");
i=i.replace(n,e[a]);
}
var o=document.createElement("span");
o.className="db topic_area",o.innerHTML=i,t.parentNode.insertBefore(o,t),t.parentNode.removeChild(t),
o.onclick=function(){
var e="/mp/topic?action=book_detail_page&isbn="+t.getAttribute("data-isbn")+"&scene=101#wechat_redirect";
location.href=e;
};
}
function a(t){
var a=t.getAttribute("data-isbn");
console.log(a),r({
url:"/mp/topic?action=get_book_info",
type:"post",
data:{
isbn:a,
biz:biz
},
success:function(a){
if(console.log(a),a=JSON.parse(a),0!=a.base_resp.ret)return void e(t);
var n={
title:a.title,
author:a.author,
img_url:a.img_url,
msg_num:a.msg_num
};
i(t,n);
},
error:function(){
e(t);
}
});
}
function n(t){
var e=t.getAttribute("data-type");
"book"===e&&a(t);
}
function o(){
var t=document.getElementsByTagName("wxtopic");
if(0!==t.length)for(var e=0;e<t.length;e++){
var i=t[e].getAttribute("data-type");
if("book"==i){
var a=t[e].getAttribute("data-isbn");
if(a==wxtopic.isbn)return void n(t[e]);
}
}
}
{
var r=t("biz_wap/utils/ajax.js");
t("biz_wap/jsapi/core.js");
}
o();
});define("appmsg/voice.js",["biz_common/utils/string/html.js","pages/voice_component.js"],function(e){
"use strict";
function i(){
var e=g("js_content");
return e?(d._oElements=e.getElementsByTagName("mpvoice")||[],d._oElements.length<=0?!1:!0):!1;
}
function t(){
d.musicLen=d._oElements.length;
}
function n(){
for(var e=0,i=0;i<d.musicLen;i++){
var t=d._oElements[i],n={};
n.voiceid=a(decodeURIComponent(t.getAttribute("voice_encode_fileid")||"")),n.voiceid=n.voiceid.replace(/&#61;/g,"="),
n.src=d.srcRoot.replace("#meidaid#",n.voiceid),n.voiceid&&"undefined"!=n.voiceid&&(o(t,n,e),
e++);
}
}
function o(e,i,t){
i.duration=1*e.getAttribute("play_length")||0,i.duration_str=s(i.duration),i.posIndex=t,
i.title=a(decodeURIComponent(e.getAttribute("name")||"")),p.renderPlayer("voice_tpl",i,e,!0),
c(i),d.musicList[i.voiceid+"_"+i.posIndex]=i;
}
function c(e){
var i=e.voiceid+"_"+e.posIndex,t=r(e.title);
e.player=p.init({
type:2,
songId:e.voiceid,
comment_id:"",
src:e.src,
duration:1*(e.duration/1e3).toFixed(2),
title:t.length>8?t.substr(0,8)+"...":t,
singer:window.nickname?window.nickname+"的语音":"公众号语音",
epname:"来自文章",
coverImgUrl:window.__appmsgCgiData.hd_head_img,
playingCss:"playing",
playCssDom:g("voice_main_"+i),
playArea:g("voice_main_"+i),
progress:g("voice_progress_"+i)
});
}
function r(e){
return e=(e||"").replace(/&#96;/g,"`").replace(/&#61;/g,"=").replace(/&#39;/g,"'").replace(/&quot;/g,'"').replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&amp;/g,"&");
}
function a(e){
return e=(e||"").replace(/&/g,"&amp;").replace(/>/g,"&gt;").replace(/</g,"&lt;").replace(/"/g,"&quot;").replace(/'/g,"&#39;").replace(/=/g,"&#61;").replace(/`/g,"&#96;");
}
function s(e){
if(isNaN(e))return"0:00";
var i=new Date(0),t=new Date(1*e),n=t.getHours()-i.getHours(),o=t.getMinutes()+60*n,c="i:ss".replace(/i|I/g,o).replace(/ss|SS/,l(t.getSeconds(),2));
return c;
}
function l(e,i){
for(var t=0,n=i-(e+"").length;n>t;t++)e="0"+e;
return e+"";
}
function g(e){
return document.getElementById(e);
}
e("biz_common/utils/string/html.js");
var p=e("pages/voice_component.js"),d={
musicList:{},
musicLen:0,
srcRoot:location.protocol+"//res.wx.qq.com/voice/getvoice?mediaid=#meidaid#"
};
if(i())return t(),n(),d.musicList;
});define("appmsg/qqmusic.js",["biz_common/utils/string/html.js","pages/voice_component.js"],function(e){
"use strict";
function i(){
var e=o("js_content");
return e?(g._oElements=e.getElementsByTagName("qqmusic")||[],g._oElements.length<=0?!1:!0):!1;
}
function t(){
g.musicLen=g._oElements.length;
}
function n(){
for(var e=0,i=0;i<g.musicLen;i++){
var t=g._oElements[i],n={};
n.musicid=s(t.getAttribute("musicid")||""),n.comment_id=s(t.getAttribute("commentid")||""),
n.musicid&&"undefined"!=n.musicid&&n.comment_id&&"undefined"!=n.comment_id&&(m(t,n,e),
e++);
}
}
function m(e,i,t){
i.media_id=s(e.getAttribute("mid")||""),i.duration=e.getAttribute("play_length")||0,
i.posIndex=t,i.musicImgPart=s(e.getAttribute("albumurl")||""),i.music_img=g.imgroot+i.musicImgPart,
i.audiourl=s(e.getAttribute("audiourl")||""),i.singer=s(e.getAttribute("singer")||""),
i.music_name=s(e.getAttribute("music_name")||""),a.renderPlayer("qqmusic_tpl",i,e,!0),
c(i),g.musicList[i.musicid+"_"+i.posIndex]=i;
}
function c(e){
var i=e.musicid+"_"+e.posIndex,t=e.comment_id+"_"+e.posIndex,n=["http://i.y.qq.com/v8/playsong.html?songmid=",e.media_id,,"&ADTAG=weixin_gzh#wechat_redirect"].join(""),m=u(e.music_name);
e.player=a.init({
type:0,
comment_id:e.comment_id,
mid:e.media_id,
songId:e.musicid,
duration:1*(e.duration/1e3).toFixed(2),
title:m.length>8?m.substr(0,8)+"...":m,
singer:window.nickname?window.nickname+"推荐的歌":"公众号推荐的歌",
epname:"QQ音乐",
coverImgUrl:e.music_img,
playingCss:"qqmusic_playing",
playCssDom:o("qqmusic_main_"+t),
playArea:o("qqmusic_play_"+i),
detailUrl:n,
detailArea:o("qqmusic_home_"+i)
});
}
function u(e){
return e=(e||"").replace(/&#96;/g,"`").replace(/&#61;/g,"=").replace(/&#39;/g,"'").replace(/&quot;/g,'"').replace(/&lt;/g,"<").replace(/&gt;/g,">").replace(/&amp;/g,"&");
}
function s(e){
return e=(e||"").replace(/&/g,"&amp;").replace(/>/g,"&gt;").replace(/</g,"&lt;").replace(/"/g,"&quot;").replace(/'/g,"&#39;").replace(/=/g,"&#61;").replace(/`/g,"&#96;");
}
function r(){}
function o(e){
return document.getElementById(e);
}
e("biz_common/utils/string/html.js");
var a=e("pages/voice_component.js"),g={
imgroot:"https://imgcache.qq.com/music/photo/mid_album_68",
musicList:{},
musicLen:0
};
if(i())return t(),n(),r(),g.musicList;
});define("appmsg/iframe.js",["biz_common/utils/string/html.js","new_video/ctl.js","pages/version4video.js","biz_common/dom/attr.js","biz_common/dom/event.js"],function(e){
"use strict";
function t(e){
var t=0;
try{
e.contentDocument&&e.contentDocument.body.offsetHeight?t=e.contentDocument.body.offsetHeight:e.Document&&e.Document.body&&e.Document.body.scrollHeight?t=e.Document.body.scrollHeight:e.document&&e.document.body&&e.document.body.scrollHeight&&(t=e.document.body.scrollHeight);
var i=e.parentElement;
if(i&&(e.style.height=t+"px"),/MSIE\s(7|8)/.test(navigator.userAgent)&&e.contentWindow&&e.contentWindow.document){
var o=e.contentWindow.document.getElementsByTagName("html");
o&&o.length&&(o[0].style.overflow="hidden");
}
}catch(n){}
}
function i(e,t){
t===!0?(d.checkOriTime=0,d.orientation!=window.orientation?(d.orientation=window.orientation,
window.mpVideoFullScreent(e)):i(e,!1)):d.checkOriTime<=2&&(d.checkOriTime++,setTimeout(function(){
d.orientation!=window.orientation?(d.checkOriTime=0,d.orientation=window.orientation,
window.mpVideoFullScreent(e)):i(e,!1);
},150));
}
function o(){
for(var e=window.pageYOffset||document.documentElement.scrollTop,t=d.video_top.length,i=e+d.innerHeight,n=0,c=0;t>c;c++){
var s=d.video_top[c];
s.reported?n++:i>=s.start&&i<=s.end&&(s.reported=!0,r.report({
step:1,
vid:s.vid
}));
}
n==t&&(a.off(window,"scroll",o),d.video_top=d.video_iframe=o=null);
}
e("biz_common/utils/string/html.js");
{
var n,r=e("new_video/ctl.js"),d={
mpVideoBotH:37,
checkOri:"orientation"in window,
innerHeight:window.innerHeight||document.documentElement.clientHeight,
video_iframe:[],
video_top:[]
},c=e("pages/version4video.js"),s=e("biz_common/dom/attr.js"),m=s.setProperty,a=e("biz_common/dom/event.js"),p=document.getElementsByTagName("iframe");
/MicroMessenger/.test(navigator.userAgent);
}
window.__setOriginStatus=function(e){
for(var t=(document.getElementById("js_content").offsetWidth,Math.ceil(3*O/4)),i=0,o=p.length;o>i;++i){
n=p[i];
var r=n.getAttribute("src")||"",c=r.match(/[\?&]vid\=([^&]*)/);
if(c&&c[1]){
var s=c[1];
2==e.ori_status&&s==e.vid&&(n.setAttribute("height",t+d.mpVideoBotH),n.setAttribute("ori_status",2));
}
}
},window.reportVid=[];
for(var u=Math.ceil(1e4*Math.random()),l=0,f=p.length;f>l;++l){
n=p[l];
var h=n.getAttribute("data-src")||"",w=n.className||"",v=n.getAttribute("src")||h;
if(!h||"#"==h){
var g=n.getAttribute("data-display-src");
if(g&&(0==g.indexOf("/cgi-bin/readtemplate?t=vote/vote-new_tmpl")||0==g.indexOf("https://mp.weixin.qq.com/cgi-bin/readtemplate?t=vote/vote-new_tmpl"))){
g=g.replace(/&amp;/g,"&");
for(var _=g.split("&"),x=["/mp/newappmsgvote?action=show"],l=0;l<_.length;l++)(0==_[l].indexOf("__biz=")||0==_[l].indexOf("supervoteid="))&&x.push(_[l]);
x.length>1&&(h=x.join("&")+"#wechat_redirect");
}
}
if(c.isShowMpVideo()&&v&&(0==v.indexOf("http://v.qq.com/iframe/player.html")||0==v.indexOf("http://v.qq.com/iframe/preview.html")||0==v.indexOf("https://v.qq.com/iframe/player.html")||0==v.indexOf("https://v.qq.com/iframe/preview.html"))){
h=h.replace(/^https:/,location.protocol),h=h.replace(/^http:/,location.protocol),
h=h.replace(/preview.html/,"player.html");
var b=v.match(/[\?&]vid\=([^&]*)/);
if(!b||!b[1])continue;
var y=b[1],O=document.getElementById("js_content").offsetWidth,k=Math.ceil(3*O/4);
window.reportVid.push(y),d.video_iframe.push({
dom:n,
vid:y
}),v=["/mp/videoplayer?video_h=",k,"&scene=",window.source,"&random_num=",u,"&article_title=",encodeURIComponent(window.msg_title.htmlDecode()),"&source=4&vid=",y,"&mid=",appmsgid,"&idx=",itemidx||idx,"&__biz=",biz,"&uin=",uin,"&key=",key,"&pass_ticket=",pass_ticket,"&version=",version,"&devicetype=",window.devicetype||"","&wxtoken=",window.wxtoken||""].join(""),
window.__addIdKeyReport&&window.__addIdKeyReport("28307",11),setTimeout(function(e,t,i,o){
return function(){
o.removeAttribute("style"),o.setAttribute("width",e),o.setAttribute("height",t),
o.setAttribute("marginWidth",0),o.setAttribute("marginHeight",0),o.style.top="0",
o.setAttribute("src",i);
};
}(O,k,v,n),0);
}else if(h&&(h.indexOf("newappmsgvote")>-1&&w.indexOf("js_editor_vote_card")>=0||0==h.indexOf("http://mp.weixin.qq.com/bizmall/appmsgcard")&&w.indexOf("card_iframe")>=0||h.indexOf("appmsgvote")>-1||h.indexOf("mp.weixin.qq.com/mp/getcdnvideourl")>-1)){
if(h=h.replace(/^http:/,location.protocol),w.indexOf("card_iframe")>=0){
var A=h.replace("#wechat_redirect",["&uin=",uin,"&key=",key,"&pass_ticket=",pass_ticket,"&scene=",source,"&msgid=",appmsgid,"&msgidx=",itemidx||idx,"&version=",version,"&devicetype=",window.devicetype||"","&child_biz=",biz,"&wxtoken=",window.wxtoken||""].join(""));
reprint_ticket&&(A+=["&mid=",mid,"&idx=",idx,"&reprint_ticket=",reprint_ticket,"&source_mid=",source_mid,"&source_idx=",source_idx].join("")),
n.setAttribute("src",A);
}else{
var j=h.indexOf("#wechat_redirect")>-1,H=["&uin=",uin,"&key=",key,"&pass_ticket=",pass_ticket,"&wxtoken=",window.wxtoken||""].join("");
reprint_ticket?H+=["&mid=",mid,"&idx=",idx,"&reprint_ticket=",reprint_ticket,"&source_mid=",source_mid,"&source_idx=",source_idx].join(""):w.indexOf("vote_iframe")>=0&&(H+=["&mid=",mid,"&idx=",idx].join(""));
var A=j?h.replace("#wechat_redirect",H):h+H;
n.setAttribute("src",A);
}
-1==h.indexOf("mp.weixin.qq.com/mp/getcdnvideourl")&&!function(e){
e.onload=function(){
t(e);
};
}(n),n.appmsg_idx=l;
}
if(h&&h.indexOf("mp.weixin.qq.com/mp/getcdnvideourl")>-1&&O>0){
var q=O,T=3*q/4;
n.width=q,n.height=T,n.style.setProperty&&(n.style.setProperty("width",q+"px","important"),
n.style.setProperty("height",T+"px","important"));
}
}
var B="onorientationchange"in window?"orientationchange":"resize";
if(a.on(window,B,function(){
for(var e=document.getElementsByTagName("iframe"),t=0,o=e.length;o>t;t++){
var n=e[t],r=n.getAttribute("src");
r&&-1!=r.indexOf("/mp/videoplayer")&&n.className.indexOf("iframe_full_video")>=0&&setTimeout(function(e){
return function(){
d.checkOri?i(e,!0):window.mpVideoFullScreent(e);
};
}(n),0);
}
},!1),a.on(window,"resize",function(){
for(var e=document.getElementsByTagName("iframe"),t=0,i=e.length;i>t;t++){
var o=e[t],n=o.getAttribute("src");
n&&-1!=n.indexOf("/mp/videoplayer")&&setTimeout(function(e){
return function(){
var t=document.getElementById("js_content").offsetWidth,i=Math.ceil(3*t/4);
2==e.getAttribute("ori_status")&&(i+=d.mpVideoBotH),e.setAttribute("width",t),e.setAttribute("height",i);
};
}(o),100);
}
},!1),window.resetMpVideoH=function(e){
var t=document.getElementById("js_content").offsetWidth,i=Math.ceil(3*t/4);
return 2==e.getAttribute("ori_status")&&(i+=d.mpVideoBotH),e.setAttribute("width",t),
e.setAttribute("height",i),m(e,"position","static","important"),!1;
},window.mpVideoFullScreent=function(e){
d.orientation=window.orientation||0;
var t=window.innerHeight,i=window.innerWidth,o=0;
if(d.checkOri&&90==Math.abs(d.orientation)){
var n=t;
t=i,i=n,o=0;
}
(e.getAttribute("height")!=t||e.getAttribute("width")!=i)&&setTimeout(function(){
m(e,"position","absolute","important"),e.setAttribute("width",i),e.setAttribute("height",t),
setTimeout(function(){
m(e,"position","fixed","important");
},20);
},0);
},window.iframe_reload=function(){
for(var e=0,i=p.length;i>e;++e){
n=p[e];
var o=n.getAttribute("src");
o&&(o.indexOf("newappmsgvote")>-1||o.indexOf("appmsgvote")>-1)&&t(n);
}
},"getElementsByClassName"in document)for(var E,z=document.getElementsByClassName("video_iframe"),l=0;E=z.item(l++);)E.setAttribute("scrolling","no"),
E.style.overflow="hidden";
d.video_iframe.length>0&&setTimeout(function(){
for(var e=d.video_iframe,t=document.getElementById("js_article"),i=0,n=e.length;n>i;i++){
var r=e[i];
if(!r||!r.dom)return;
for(var c=r.dom,s=c.offsetHeight,m=0;c&&t!==c;)m+=c.offsetTop,c=c.offsetParent;
d.video_top.push({
start:m+s/2,
end:m+s/2+d.innerHeight,
reported:!1,
vid:r.vid
});
}
o(),a.on(window,"scroll",o);
},0);
});define("appmsg/review_image.js",["biz_common/dom/event.js","biz_wap/jsapi/core.js","biz_common/utils/url/parse.js","appmsg/cdn_img_lib.js"],function(e){
"use strict";
function t(e,t){
a.invoke("imagePreview",{
current:e,
urls:t
},function(){
window.__addIdKeyReport&&window.__addIdKeyReport("28307","2");
});
}
function i(e){
var i=[],a=e.container,n=e.imgs||[];
if(a)for(var o=a.getElementsByTagName("img")||[],p=0,m=o.length;m>p;p++)n.push(o.item(p));
for(var p=0,m=n.length;m>p;p++){
var c=n[p],d=c.getAttribute("data-src")||c.getAttribute("src"),u=c.getAttribute("data-type");
if(d&&-1==d.indexOf("wx_fmt=gif")){
for(;-1!=d.indexOf("?tp=webp");)d=d.replace("?tp=webp","");
c.dataset&&c.dataset.s&&d.isCDN()&&(d=d.replace(/\/640$/,"/0"),d=d.replace(/\/640\?/,"/0?")),
d.isCDN()&&(d=s.addParam(d,"wxfrom","3",!0)),e.is_https_res&&(d=d.http2https()),
u&&(d=s.addParam(d,"wxtype",u,!0)),i.push(d),function(e){
r.on(c,"click",function(){
return t(e,i),!1;
});
}(d);
}
}
}
var r=e("biz_common/dom/event.js"),a=e("biz_wap/jsapi/core.js"),s=e("biz_common/utils/url/parse.js");
return e("appmsg/cdn_img_lib.js"),i;
});define("appmsg/outer_link.js",["biz_common/dom/event.js"],function(e){
"use strict";
function n(e){
var n=e.container;
if(!n)return!1;
for(var r=n.getElementsByTagName("a")||[],i=0,o=r.length;o>i;++i)!function(n){
var i=r[n],o=i.getAttribute("href");
if(!o)return!1;
var a=0,c=i.innerHTML;
/^[^<>]+$/.test(c)?a=1:/^<img[^>]*>$/.test(c)&&(a=2),!!e.changeHref&&(o=e.changeHref(o,a)),
t.on(i,"click",function(){
return location.href=o,!1;
},!0);
}(i);
}
var t=e("biz_common/dom/event.js");
return n;
});define("biz_wap/jsapi/core.js",[],function(){
"use strict";
document.domain="qq.com";
var n=window.__moon_report||function(){},o=8,e={
ready:function(e){
var i=function(){
try{
e&&e();
}catch(i){
throw n([{
offset:o,
log:"ready",
e:i
}]),i;
}
};
"undefined"!=typeof top.window.WeixinJSBridge&&top.window.WeixinJSBridge.invoke?i():top.window.document.addEventListener?top.window.document.addEventListener("WeixinJSBridgeReady",i,!1):top.window.document.attachEvent&&(top.window.document.attachEvent("WeixinJSBridgeReady",i),
top.window.document.attachEvent("onWeixinJSBridgeReady",i));
},
invoke:function(e,i,t){
this.ready(function(){
return"object"!=typeof top.window.WeixinJSBridge?(alert("请在微信中打开此链接！"),!1):void top.window.WeixinJSBridge.invoke(e,i,function(i){
try{
if(t){
t.apply(window,arguments);
var d=i&&i.err_msg?", err_msg-> "+i.err_msg:"";
console.debug("[jsapi] invoke->"+e+d);
}
}catch(r){
throw n([{
offset:o,
log:"invoke",
e:r
}]),r;
}
});
});
},
call:function(e){
this.ready(function(){
if("object"!=typeof top.window.WeixinJSBridge)return!1;
try{
top.window.WeixinJSBridge.call(e);
}catch(i){
throw n([{
offset:o,
log:"call",
e:i
}]),i;
}
});
},
on:function(e,i){
this.ready(function(){
return"object"==typeof top.window.WeixinJSBridge&&top.window.WeixinJSBridge.on?void top.window.WeixinJSBridge.on(e,function(){
try{
i&&i.apply(window,arguments);
}catch(e){
throw n([{
offset:o,
log:"on",
e:e
}]),e;
}
}):!1;
});
}
};
return e;
});define("biz_common/dom/event.js",[],function(){
"use strict";
function e(e,t,n,o){
a.isPc||a.isWp?i(e,"click",o,t,n):i(e,"touchend",o,function(e){
if(-1==a.tsTime||+new Date-a.tsTime>200)return a.tsTime=-1,!1;
var n=e.changedTouches[0];
return Math.abs(a.y-n.clientY)<=5&&Math.abs(a.x-n.clientX)<=5?t.call(this,e):void 0;
},n);
}
function t(e,t){
if(!e||!t||e.nodeType!=e.ELEMENT_NODE)return!1;
var n=e.webkitMatchesSelector||e.msMatchesSelector||e.matchesSelector;
return n?n.call(e,t):(t=t.substr(1),e.className.indexOf(t)>-1);
}
function n(e,n,i){
for(;e&&!t(e,n);)e=e!==i&&e.nodeType!==e.DOCUMENT_NODE&&e.parentNode;
return e;
}
function i(t,i,o,r,c){
var s,d,u;
return"input"==i&&a.isPc,t?("function"==typeof o&&(c=r,r=o,o=""),"string"!=typeof o&&(o=""),
t==window&&"load"==i&&/complete|loaded/.test(document.readyState)?r({
type:"load"
}):"tap"==i?e(t,r,c,o):(s=function(e){
var t=r(e);
return t===!1&&(e.stopPropagation&&e.stopPropagation(),e.preventDefault&&e.preventDefault()),
t;
},o&&"."==o.charAt(0)&&(u=function(e){
var i=e.target||e.srcElement,r=n(i,o,t);
return r?(e.delegatedTarget=r,s(e)):void 0;
}),d=u||s,r[i+"_handler"]=d,t.addEventListener?void t.addEventListener(i,d,!!c):t.attachEvent?void t.attachEvent("on"+i,d,!!c):void 0)):void 0;
}
function o(e,t,n,i){
if(e){
var o=n[t+"_handler"]||n;
return e.removeEventListener?void e.removeEventListener(t,o,!!i):e.detachEvent?void e.detachEvent("on"+t,o,!!i):void 0;
}
}
var r=navigator.userAgent,a={
isPc:/(WindowsNT)|(Windows NT)|(Macintosh)/i.test(navigator.userAgent),
isWp:/Windows\sPhone/i.test(r),
tsTime:-1
};
return a.isPc||i(document,"touchstart",function(e){
var t=e.changedTouches[0];
a.x=t.clientX,a.y=t.clientY,a.tsTime=+new Date;
}),{
on:i,
off:o,
tap:e
};
});define("appmsg/copyright_report.js",["biz_common/dom/event.js"],function(e){
"use strict";
function o(e){
var o=["/mp/copyrightreport?action=report&biz=",biz,"&scene=",e.scene,"&ori_username=",source_username,"&user_uin=",user_uin,"&uin=",uin,"&key=",key,"&pass_ticket=",pass_ticket,"&t=",Math.random()].join("");
window.isSg&&(o+="&from=sougou");
var t=new Image;
t.src=o.substr(0,1024);
}
function t(){
var e=__appmsgCgiData;
if("2"==e.copyright_stat){
for(var o=r("copyright_info"),t=r("js_article");o&&t!==o;)c.copyright_top+=o.offsetTop,
o=o.offsetParent;
i.on(window,"scroll",n);
}
}
function n(){
var e=window.pageYOffset||document.documentElement.scrollTop;
e+c.innerHeight>c.copyright_top&&(o({
scene:"1",
card_pos:"0"
}),i.off(window,"scroll",n),n=c.copyright_top=null);
}
function r(e){
return document.getElementById(e);
}
var i=e("biz_common/dom/event.js"),c={
innerHeight:window.innerHeight||document.documentElement.clientHeight,
copyright_top:0
};
return{
card_click_report:o,
card_pv_report:t
};
});define("appmsg/cache.js",["biz_wap/jsapi/core.js","biz_common/utils/monitor.js"],function(e){
"use strict";
function i(){
o();
}
function n(e,i,n,o){
0>o||setTimeout(function(){
"avg"==e?a.setAvg(i,n,o):a.setSum(i,n,o),a.send();
},1150);
}
function o(){
var e=write_sceen_time-window.logs.pagetime.html_begin,i=Math.random()>=.8,o=!1;
try{
var a=t(decodeURIComponent(window.uin));
o=Math.ceil(a/100)%2!=0?!0:!1;
}catch(s){
o=!1;
}
if(window.isInWeixinApp()&&o){
for(var m=(location.href.replace(/\&key\=.*$/g,"#rd"),[]),f=document.getElementsByTagName("link"),u=0;u<f.length;u++)"stylesheet"==f[u].rel&&m.push(f[u].href);
r.invoke("cache",{
disable:!1,
appId:c,
resourceList:m
},function(o){
o&&"cache:ok"==o.err_msg&&i&&n("avg",27822,49,e);
});
}else window.isInWeixinApp()&&i&&n("avg",27822,47,e);
}
function t(e){
var i,n,o,t,r={},a=0,c=0,s="",m=String.fromCharCode,f=e.length,u="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
for(i=0;64>i;i++)r[u.charAt(i)]=i;
for(o=0;f>o;o++)for(n=r[e.charAt(o)],a=(a<<6)+n,c+=6;c>=8;)((t=a>>>(c-=8)&255)||f-2>o)&&(s+=m(t));
return s;
}
var r=e("biz_wap/jsapi/core.js"),a=e("biz_common/utils/monitor.js"),c="wx3be6367203f983ac";
i();
});define("appmsg/pay_for_reading.js",["biz_common/dom/class.js","biz_common/dom/event.js","biz_wap/utils/ajax.js","biz_wap/jsapi/pay.js","biz_common/utils/spin.js"],function(e,t,a,s){
"use strict";
function n(e){
e&&(e.style.display="block");
}
function o(e){
e&&(e.style.display="none");
}
function i(e){
u=!0,p.addClass(l.pay,"disabled"),n(l.toast),d({
url:"/mp/payforread?action=pay",
type:"POST",
data:{
appmsgid:appmsgid,
__biz:biz,
idx:idx,
fee:e,
timestamp:pay_timestamp
},
success:function(e){
try{
e=JSON.parse(e);
}catch(t){
e={},_.src="/mp/jsreport?key=42&content=type:jsonparseerr&r="+Math.random();
}
if(e&&e.base_resp){
var a=+e.base_resp.ret;
if(0==a)r(e.package_info);else{
switch(u=!1,a){
case-6:
s("操作过于频繁，请稍后重试");
break;

case 155001:
n(l.tips),c.on(l.tipsOK,"touchend",function i(t){
o(l.tips),r(e.package_info),t.preventDefault(),c.off(l.tipsOK,"touchend",i);
});
break;

case 155002:
s("重复付费");
break;

case 155003:
s("该文章已关闭付费，可以免费阅读了"),location.reload();
break;

case 155004:
s("该帐号已被封，不能进行支付");
break;

case 155005:
s("该文章已被删除");
break;

case 155006:
s("该文章已被取消原创声明，不需要支付");
break;

case 268502026:
s("你今日的微信支付已达上限，请择日再付费");
break;

case 268502027:
s("该公众号已达到微信支付的收款最高限额，不能再进行付费");
break;

case 268502028:
s("该公众号今日的收款额度已达上限，请择日再付费");
break;

case 268502029:
s("该公众号已达到微信支付的收款限额，不能再进行付费");
break;

default:
s("系统错误，请重试");
}
_.src="/mp/jsreport?key=42&content=type:resperr;ret:"+a+"&r="+Math.random();
}
}
},
error:function(){
u=!1,s("系统错误，请重试"),_.src="/mp/jsreport?key=42&content=type:ajaxerr&r="+Math.random();
},
complete:function(){
p.removeClass(l.pay,"disabled"),o(l.toast);
}
});
}
function r(e){
e.success=function(){
u=!1,location.reload();
},e.error=function(e){
u=!1,-1==e.indexOf(":cancel")&&(_.src="/mp/jsreport?key=43&content=type:jsapierr;msg:"+e+"&r="+Math.random());
},m.pay(e);
}
if(document.getElementById("js_pay_area")){
var p=e("biz_common/dom/class.js");
if(!window.uin||!window.key||!/micromessenger/i.test(window.navigator.userAgent)||/WindowsWechat/i.test(window.navigator.userAgent))return document.getElementById("js_pay_desc").innerText="文章已设置需要付费才能阅读，请在手机微信内进行付费",
void p.addClass(document.getElementById("js_pay_btn"),"disabled");
var c=e("biz_common/dom/event.js"),d=e("biz_wap/utils/ajax.js"),m=e("biz_wap/jsapi/pay.js"),y=e("biz_common/utils/spin.js"),l={
pay:document.getElementById("js_pay_btn"),
tips:document.getElementById("js_pay_tips"),
tipsOK:document.getElementById("js_pay_tips_ok"),
toast:document.getElementById("js_pay_toast")
},u=!1,_=new Image;
!function(){
{
var e=document.getElementById("js_pay_spinner");
new y({
top:"38%",
lines:10,
width:4,
length:8,
radius:8,
color:"#FFF"
}).spin(e);
}
}(),c.on(l.pay,"tap",function(){
i(pay_fee);
});
}
});define("appmsg/async.js",["biz_common/utils/string/html.js","appmsg/a_tpl.html.js","appmsg/img_copyright_tpl.html.js","biz_common/dom/event.js","biz_wap/utils/ajax.js","biz_common/dom/class.js","biz_wap/jsapi/core.js","biz_common/tmpl.js","biz_wap/utils/storage.js","rt/appmsg/getappmsgext.rt.js","pages/version4video.js","appmsg/cdn_img_lib.js","biz_common/utils/url/parse.js","appmsg/a.js","appmsg/like.js","appmsg/comment.js","appmsg/reward_entry.js","appmsg/iframe.js"],function(require,exports,module,alert){
"use strict";
function saveCopy(e){
var t={};
for(var i in e)if(e.hasOwnProperty(i)){
var r=e[i],a=typeof r;
r="string"==a?r.htmlDecode():r,"object"==a&&(r=saveCopy(r)),t[i]=r;
}
return t;
}
function img_copyright(e){
if(e&&e.img_copy_info&&e.img_copy_info.list){
for(var t={},i=e.img_copy_info.list,r=window.__appmsgCgiData.copyright_stat,a=window.__appmsgCgiData.source_biz,n=0,o=i.length;o>n;n++){
var s=i[n];
if(2==s.type){
if(2==r&&a==s.source_uin)continue;
t[s.img_url]={
source_nickname:s.source_nickname,
source_uin:s.source_uin,
source_username:s.source_username||"",
source_sn:s.sn||""
};
}
}
for(var p=document.getElementsByTagName("img"),n=0,o=p.length;o>n;n++){
var s=p[n],d=s.getAttribute("data-src")||s.getAttribute("data-backsrc")||"";
if(t[d]){
var m=document.createElement("div");
m.innerHTML=TMPL.tmpl(img_copyright_tpl,t[d]);
{
var c=m.children[0],_=s.parentNode,l=_.insertBefore(c,s),f=l.childNodes[0];
(function(e){
DomEvent.on(l,"click",function(){
var t="https://mp.weixin.qq.com/mp/profile_ext?action=home&username="+e.source_username+"&sn="+e.source_sn+"#wechat_redirect";
JSAPI.invoke("openUrlWithExtraWebview",{
url:t,
openType:1
},function(e){
-1==e.err_msg.indexOf("ok")&&(location.href=t);
});
});
})(t[d]);
}
l.insertBefore(s,f);
}
}
}
}
function fillVedio(e){
if(vedio_iframes&&vedio_iframes.length>0)for(var t,i,r,a=0,n=vedio_iframes.length;n>a;++a)t=vedio_iframes[a],
i=t.iframe,r=t.src,e&&(r=r.replace(/\&encryptVer=[^\&]*/gi,""),r=r.replace(/\&platform=[^\&]*/gi,""),
r=r.replace(/\&cKey=[^\&]*/gi,""),r=r+"&encryptVer=6.0&platform=61001&cKey="+e),
i.setAttribute("src",r);
}
function fillData(e){
var t=e.adRenderData||{
advertisement_num:0
};
if(!t.flag&&t.advertisement_num>0){
var i=t.advertisement_num,r=t.advertisement_info;
window.adDatas.num=i;
for(var a=0;i>a;++a){
var n=null,o=r[a];
if(o.biz_info=o.biz_info||{},o.app_info=o.app_info||{},o.pos_type=o.pos_type||0,
o.logo=o.logo||"",100==o.pt)n={
usename:o.biz_info.user_name,
pt:o.pt,
url:o.url,
traceid:o.traceid,
adid:o.aid,
ticket:o.ticket,
is_appmsg:!0
};else if(102==o.pt)n={
appname:o.app_info.app_name,
versioncode:o.app_info.version_code,
pkgname:o.app_info.apk_name,
androiddownurl:o.app_info.apk_url,
md5sum:o.app_info.app_md5,
signature:o.app_info.version_code,
rl:o.rl,
traceid:o.traceid,
pt:o.pt,
ticket:o.ticket,
type:o.type,
adid:o.aid,
is_appmsg:!0
};else if(101==o.pt)n={
appname:o.app_info.app_name,
app_id:o.app_info.app_id,
icon_url:o.app_info.icon_url,
appinfo_url:o.app_info.appinfo_url,
rl:o.rl,
traceid:o.traceid,
pt:o.pt,
ticket:o.ticket,
type:o.type,
adid:o.aid,
is_appmsg:!0
};else if(103==o.pt||104==o.pt||2==o.pt&&o.app_info){
var s=o.app_info.down_count||0,p=o.app_info.app_size||0,d=o.app_info.app_name||"",m=o.app_info.category,c=["万","百万","亿"];
if(s>=1e4){
s/=1e4;
for(var _=0;s>=10&&2>_;)s/=100,_++;
s=s.toFixed(1)+c[_]+"次";
}else s=s.toFixed(1)+"次";
p>=1024?(p/=1024,p=p>=1024?(p/1024).toFixed(2)+"MB":p.toFixed(2)+"KB"):p=p.toFixed(2)+"B",
m=m?m[0]||"其他":"其他";
for(var l=["-","(",":",'"',"'","：","（","—","－","“","‘"],f=-1,u=0,g=l.length;g>u;++u){
var w=l[u],v=d.indexOf(w);
-1!=v&&(-1==f||f>v)&&(f=v);
}
-1!=f&&(d=d.substring(0,f)),o.app_info._down_count=s,o.app_info._app_size=p,o.app_info._category=m,
o.app_info.app_name=d,n={
appname:o.app_info.app_name,
app_rating:o.app_info.app_rating||0,
app_id:o.app_info.app_id,
channel_id:o.app_info.channel_id,
md5sum:o.app_info.app_md5,
rl:o.rl,
pkgname:o.app_info.apk_name,
androiddownurl:o.app_info.apk_url,
versioncode:o.app_info.version_code,
appinfo_url:o.app_info.appinfo_url,
traceid:o.traceid,
pt:o.pt,
url:o.url,
ticket:o.ticket,
type:o.type,
adid:o.aid,
is_appmsg:!0
};
}else if(105==o.pt){
var h=o.card_info.card_id||"",y=o.card_info.card_ext||"";
y=y.htmlDecode();
try{
y=JSON.parse(y),y.outer_str=o.card_info.card_outer_id||"",y=JSON.stringify(y);
}catch(j){
y="{}";
}
n={
card_id:h,
card_ext:y,
pt:o.pt,
ticket:o.ticket||"",
url:o.url,
rl:o.rl,
tid:o.traceid,
traceid:o.traceid,
type:o.type,
adid:o.aid,
is_appmsg:!0
};
}else if(106==o.pt){
var x=o.mp_shop_info.pid||"",b=o.mp_shop_info.outer_id||"";
n={
pid:x,
outer_id:b,
pt:o.pt,
url:o.url,
rl:o.rl,
tid:o.traceid,
traceid:o.traceid,
type:o.type,
adid:o.aid,
is_appmsg:!0
};
}
var k=o.image_url;
require("appmsg/cdn_img_lib.js");
var q=require("biz_common/utils/url/parse.js");
k&&k.isCDN()&&(k=k.replace(/\/0$/,"/640"),k=k.replace(/\/0\?/,"/640?"),o.image_url=q.addParam(k,"wxfrom","50",!0)),
adDatas.ads["pos_"+o.pos_type]={
a_info:o,
adData:n
};
}
var z=function(e){
var t=window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop;
"undefined"!=typeof e&&(t=e);
10>=t&&(I.style.display="block",DomEvent.off(window,"scroll",z));
},E=document.getElementById("js_bottom_ad_area"),I=document.getElementById("js_top_ad_area"),D=adDatas.ads;
for(var O in D)if(0==O.indexOf("pos_")){
var n=D[O],o=!!n&&n.a_info;
if(n&&o)if(0==o.pos_type)E.innerHTML=TMPL.tmpl(a_tpl,o);else if(1==o.pos_type){
I.style.display="none",I.innerHTML=TMPL.tmpl(a_tpl,o),DomEvent.on(window,"scroll",z);
var M=0;
window.localStorage&&(M=1*localStorage.getItem(O)||0),window.scrollTo(0,M),z(M);
}
}
require("appmsg/a.js");
}
var S=e.appmsgstat||{};
window.appmsgstat||(window.appmsgstat=S),S.show&&(!function(){
var e=document.getElementById("js_read_area3"),t=document.getElementById("like3");
e.style.display="block",t.style.display="inline",S.liked&&Class.addClass(t,"praised"),
t.setAttribute("like",S.liked?"1":"0");
var i=document.getElementById("likeNum3"),r=document.getElementById("readNum3"),a=S.read_num,n=S.like_num;
a||(a=1),n||(n="Like"),parseInt(a)>1e5?a="100000+":"",parseInt(n)>1e5?n="100000+":"",
r&&(r.innerHTML=a),i&&(i.innerHTML=n);
}(),require("appmsg/like.js")),1==e.comment_enabled&&(window.can_fans_comment_only=e.only_fans_can_comment,
require("appmsg/comment.js")),-1==ua.indexOf("WindowsWechat")&&-1!=ua.indexOf("MicroMessenger")&&e.reward&&(rewardEntry=require("appmsg/reward_entry.js"),
rewardEntry.handle(e.reward,getCountPerLine()));
}
function getAsyncData(){
var is_need_ticket="";
vedio_iframes&&vedio_iframes.length>0&&(is_need_ticket="&is_need_ticket=1");
var is_need_ad=1,_adInfo=null;
if(window.localStorage)try{
var key=[biz,sn,mid,idx].join("_"),_ad=adLS.get(key);
_adInfo=_ad.info;
try{
_adInfo=eval("("+_adInfo+")");
}catch(e){
_adInfo=null;
}
var _adInfoSaveTime=_ad.time,_now=+new Date;
_adInfo&&18e4>_now-1*_adInfoSaveTime&&1*_adInfo.advertisement_num>0?is_need_ad=0:adLS.remove(key);
}catch(e){
is_need_ad=1,_adInfo=null;
}
(!document.getElementsByClassName||-1==navigator.userAgent.indexOf("MicroMessenger")||inwindowwx)&&(is_need_ad=0);
var screen_num=Math.ceil(document.body.scrollHeight/(document.documentElement.clientHeight||window.innerHeight)),both_ad=screen_num>=2?1:0;
ajax({
url:"/mp/getappmsgext?__biz="+biz+"&appmsg_type="+appmsg_type+"&mid="+mid+"&sn="+sn+"&idx="+idx+"&scene="+source+"&title="+encodeURIComponent(msg_title.htmlDecode())+"&ct="+ct+"&devicetype="+devicetype.htmlDecode()+"&version="+version.htmlDecode()+"&f=json&r="+Math.random()+is_need_ticket+"&is_need_ad="+is_need_ad+"&comment_id="+comment_id+"&is_need_reward="+is_need_reward+"&both_ad="+both_ad+"&reward_uin_count="+(is_need_reward?3*getCountPerLine():0),
data:{
is_only_read:is_only_read
},
type:"POST",
dataType:"json",
rtId:"27613",
rtKey:"50",
rtDesc:rtGetAppmsgExt,
async:!0,
success:function(e){
if(e)try{
if(e&&e.base_resp&&e.base_resp.wxtoken&&(window.wxtoken=e.base_resp.wxtoken),window.fromWeixinCached&&require("appmsg/iframe.js"),
fillVedio(e.appmsgticket?e.appmsgticket.ticket:""),img_copyright(e),e.ret)return;
var t={};
if(0==is_need_ad)t=_adInfo,t||(t={
advertisement_num:0
});else{
if(e.advertisement_num>0&&e.advertisement_info){
var i=e.advertisement_info;
t.advertisement_info=saveCopy(i);
}
t.advertisement_num=e.advertisement_num;
}
1==is_need_ad&&(window._adRenderData=t),fillData({
adRenderData:t,
appmsgstat:e.appmsgstat,
comment_enabled:e.comment_enabled,
only_fans_can_comment:e.only_fans_can_comment,
reward:{
reward_total:e.reward_total_count,
reward_head_imgs:e.reward_head_imgs||[],
can_reward:e.can_reward,
timestamp:e.timestamp
}
});
}catch(r){
var a=new Image;
return a.src=("http://mp.weixin.qq.com/mp/jsreport?1=1&key=1&content=biz:"+biz+",mid:"+mid+",uin:"+uin+"[key1]"+encodeURIComponent(r.toString())+"&r="+Math.random()).substr(0,1024),
void(console&&console.error(r));
}
},
error:function(){
var e=new Image;
e.src="http://mp.weixin.qq.com/mp/jsreport?1=1&key=2&content=biz:"+biz+",mid:"+mid+",uin:"+uin+"[key2]ajax_err&r="+Math.random();
}
});
}
function getCountPerLine(){
return DomEvent.on(window,"resize",function(){
onResize(),rewardEntry&&rewardEntry.render(getCountPerLine());
}),onResize();
}
function onResize(){
var e=window.innerWidth||document.documentElement.clientWidth;
try{
var t=document.getElementById("page-content").getBoundingClientRect();
t.width&&(e=t.width);
}catch(i){}
var r=30,a=34,n=Math.floor(.9*(e-r)/a);
return document.getElementById("js_reward_inner")&&(document.getElementById("js_reward_inner").style.width=n*a+"px"),
getCountPerLine=function(){
return n;
},n;
}
require("biz_common/utils/string/html.js");
var a_tpl=require("appmsg/a_tpl.html.js"),img_copyright_tpl=require("appmsg/img_copyright_tpl.html.js"),iswifi=!1,ua=navigator.userAgent,in_mm=-1!=ua.indexOf("MicroMessenger"),inwindowwx=-1!=navigator.userAgent.indexOf("WindowsWechat"),DomEvent=require("biz_common/dom/event.js"),offset=200,ajax=require("biz_wap/utils/ajax.js"),Class=require("biz_common/dom/class.js"),JSAPI=require("biz_wap/jsapi/core.js"),TMPL=require("biz_common/tmpl.js"),LS=require("biz_wap/utils/storage.js"),rtGetAppmsgExt=require("rt/appmsg/getappmsgext.rt.js"),rewardEntry,adLS=new LS("ad"),iframes=document.getElementsByTagName("iframe"),iframe,js_content=document.getElementById("js_content"),vedio_iframes=[],w=js_content.offsetWidth,h=3*w/4;
window.logs.video_cnt=0;
for(var i=0,len=iframes.length;len>i;++i){
iframe=iframes[i];
var src=iframe.getAttribute("data-src")||"",realsrc=iframe.getAttribute("src")||src;
if(realsrc){
var Version4video=require("pages/version4video.js");
if(!Version4video.isShowMpVideo()&&(0==realsrc.indexOf("http://v.qq.com/iframe/player.html")||0==realsrc.indexOf("https://v.qq.com/iframe/player.html")||0==realsrc.indexOf("http://v.qq.com/iframe/preview.html")||0==realsrc.indexOf("https://v.qq.com/iframe/preview.html"))||0==realsrc.indexOf("http://z.weishi.com/weixin/player.html")){
-1==realsrc.indexOf("http://z.weishi.com/weixin/player.html")&&-1==src.indexOf("http://z.weixin.com/weixin/player.html")&&(src=src.replace(/^https:/,location.protocol),
src=src.replace(/^http:/,location.protocol),src=src.replace(/preview.html/,"player.html"),
realsrc=realsrc.replace(/^https:/,location.protocol),realsrc=realsrc.replace(/^http:/,location.protocol),
realsrc=realsrc.replace(/preview.html/,"player.html")),realsrc=realsrc.replace(/width=\d+/g,"width="+w),
realsrc=realsrc.replace(/height=\d+/g,"height="+h),in_mm&&(0==realsrc.indexOf("http://v.qq.com/iframe/player.html")||0==realsrc.indexOf("https://v.qq.com/iframe/player.html"))||in_mm&&(0==realsrc.indexOf("http://v.qq.com/iframe/preview.html")||0==realsrc.indexOf("https://v.qq.com/iframe/preview.html"))?vedio_iframes.push({
iframe:iframe,
src:realsrc
}):iframe.setAttribute("src",realsrc),iframe.width=w,iframe.height=h,iframe.style.setProperty&&(iframe.style.setProperty("width",w+"px","important"),
iframe.style.setProperty("height",h+"px","important")),window.__addIdKeyReport&&window.__addIdKeyReport("28307",10),
window.logs.video_cnt++;
continue;
}
}
}
window.adDatas={
ads:{},
num:0
};
var js_toobar=document.getElementById("js_toobar3"),innerHeight=window.innerHeight||document.documentElement.clientHeight,onScroll=function(){
var e=window.pageYOffset||document.documentElement.scrollTop,t=js_toobar.offsetTop;
e+innerHeight+offset>=t&&(getAsyncData(),DomEvent.off(window,"scroll",onScroll));
};
iswifi?(DomEvent.on(window,"scroll",onScroll),onScroll()):getAsyncData();
});