define("biz_wap/ui/lazyload_img.js",["biz_wap/utils/mmversion.js","biz_common/dom/event.js","biz_common/dom/attr.js","biz_common/ui/imgonepx.js"],function(t){
"use strict";
function i(){
var t=this.images;
if(!t||t.length<=0)return!1;
var i=window.pageYOffset||document.documentElement.scrollTop,e=window.innerHeight||document.documentElement.clientHeight,o=e+40,n=this.offset||20,r=0;
if("wifi"==window.networkType){
var s={
bottom:1,
top:1
};
this.lazyloadHeightWhenWifi&&(s=this.lazyloadHeightWhenWifi()),n=Math.max(s.bottom*e,n),
r=Math.max(s.top*e,r);
}
for(var l=+new Date,d=[],u=this.sw,f=this,w=0,p=t.length;p>w;w++)!function(t,e){
var s=t.el.offsetTop,l=t.src;
if(l){
var f=r,w=n;
-1!=l.indexOf("wx_fmt=gif")&&c&&(f=0,w=20),!t.show&&(i>=s&&i<=s+t.height+f||s>i&&i+o+w>s)&&(e.inImgRead&&(i>=s&&i<=s+t.height||s>i&&i+o>s)&&e.inImgRead(l,networkType),
e.changeSrc&&(l=e.changeSrc(t.el,l)),t.el.onerror=function(){
var t=this;
!!e.onerror&&e.onerror(l,t);
},t.el.onload=function(){
var t=this;
h(t,"height","auto","important"),t.getAttribute("_width")?h(t,"width",t.getAttribute("_width"),"important"):h(t,"width","auto","important"),
!!e.onload&&e.onload(l,t);
},m(t.el,"src",l),d.push(l),t.show=!0,h(t.el,"visibility","visible","important")),
a.isWp&&1*t.el.width>u&&(t.el.width=u);
}
}(t[w],f);
d.length>0&&this.detect&&this.detect({
time:l,
loadList:d,
scrollTop:i
});
}
function e(){
var t=document.getElementsByTagName("img"),e=[],o=this.container,n=this.attrKey||"data-src",a=o.offsetWidth,r=0;
o.currentStyle?r=o.currentStyle.width:"undefined"!=typeof getComputedStyle&&(r=getComputedStyle(o).width),
this.sw=1*r.replace("px","");
for(var s=0,d=t.length;d>s;s++){
var c=t.item(s),u=m(c,n);
if(u){
var f=100;
if(c.dataset&&c.dataset.ratio){
var w=1*c.dataset.ratio,p=1*c.dataset.w||a;
"number"==typeof w&&w>0?(p=a>=p?p:a,f=p*w,c.style.width&&c.setAttribute("_width",c.style.width),
h(c,"width",p+"px","important"),h(c,"visibility","visible","important"),c.setAttribute("src",l)):h(c,"visibility","hidden","important");
}else h(c,"visibility","hidden","important");
h(c,"height",f+"px","important"),e.push({
el:c,
src:u,
height:f,
show:!1
});
}
}
this.images=e,i.call(this);
}
function o(t){
var e=this,o=e.timer;
clearTimeout(o),e.timer=setTimeout(function(){
i.call(e,t);
},300);
}
function n(t){
r.on(window,"scroll",function(i){
o.call(t,i);
}),r.on(window,"load",function(i){
e.call(t,i);
}),r.on(document,"touchmove",function(i){
o.call(t,i);
});
}
var a=t("biz_wap/utils/mmversion.js"),r=t("biz_common/dom/event.js"),s=t("biz_common/dom/attr.js"),m=s.attr,h=s.setProperty,l=t("biz_common/ui/imgonepx.js"),d=new Date,c=(d.getHours(),
!0);
return n;
});define("biz_common/log/jserr.js",[],function(){
function e(e,n){
return e?(r.replaceStr&&(e=e.replace(r.replaceStr,"")),n&&(e=e.substr(0,n)),encodeURIComponent(e.replace("\n",","))):"";
}
var r={};
return window.onerror=function(n,o,t,c,i){
return"Script error."==n||o?"undefined"==typeof r.key||"undefined"==typeof r.reporturl?!0:void setTimeout(function(){
c=c||window.event&&window.event.errorCharacter||0;
var l=[];
if(l.push("msg:"+e(n,100)),o&&(o=o.replace(/[^\,]*\/js\//g,"")),l.push("url:"+e(o,200)),
l.push("line:"+t),l.push("col:"+c),i&&i.stack)l.push("info:"+e(i.stack.toString(),200));else if(arguments.callee){
for(var s=[],u=arguments.callee.caller,a=3;u&&--a>0&&(s.push(u.toString()),u!==u.caller);)u=u.caller;
s=s.join(","),l.push("info:"+e(s,200));
}
var p=new Image;
if(p.src=(r.reporturl+"&key="+r.key+"&content="+l.join("||")).substr(0,1024),window.console&&window.console.log){
var f=l.join("\n");
try{
f=decodeURIComponent(f);
}catch(d){}
console.log(f);
}
},0):!0;
},function(e){
r=e;
};
});define("appmsg/share.js",["biz_common/utils/string/html.js","appmsg/cdn_img_lib.js","biz_common/dom/event.js","biz_common/utils/url/parse.js","biz_wap/utils/mmversion.js","biz_wap/utils/ajax.js","biz_wap/jsapi/core.js"],function(i){
"use strict";
function e(i,e){
var n="";
""!=tid&&(n="tid="+tid+"&aid=54");
var t=i.split("?")[1]||"";
if(t=t.split("#")[0],""!=t){
var o=[t,"scene="+e,"srcid="+srcid];
return""!=n&&o.push(n),t=o.join("&"),i.split("?")[0]+"?"+t+"#"+(i.split("#")[1]||"");
}
}
function n(i,e,n){
var t=i.split("?").pop();
if(t=t.split("#").shift(),""!=t){
var o=[t,"action_type="+n,"scene="+window.source,"vid="+("undefined"!=typeof window.reportVid?window.reportVid.join(";"):""),"musicid="+("undefined"!=typeof window.reportMid?window.reportMid.join(";"):""),"voiceid="+("undefined"!=typeof window.reportVoiceid?window.reportVoiceid.join(";"):"")].join("&");
r({
url:"/mp/appmsg/show",
type:"POST",
data:o
});
}
}
function t(i,e){
return i.isCDN()&&(i=o.addParam(i,"wxfrom",e,!0)),i;
}
i("biz_common/utils/string/html.js"),i("appmsg/cdn_img_lib.js");
var o=(i("biz_common/dom/event.js"),i("biz_common/utils/url/parse.js")),s=i("biz_wap/utils/mmversion.js"),r=i("biz_wap/utils/ajax.js"),m=i("biz_wap/jsapi/core.js");
m.call("hideToolbar"),m.call("showOptionMenu");
var a=msg_title.htmlDecode(),d=(msg_source_url.htmlDecode(),""),l=msg_cdn_url||round_head_img,c=msg_link.htmlDecode(),a=msg_title.htmlDecode(),u=msg_desc.htmlDecode();
u=u||c,u=u.replace(/<br\/>/g,"\n"),idx>1&&document.getElementById("js_content")&&1446652800>ct&&(u=document.getElementById("js_content").innerHTML.replace(/<\/?[^>]*\/?>/g,"").htmlDecode().replace(/^(\s*)|(\s*)$/g,"").substr(0,54)),
l.isCDN()&&(l=l.replace(/\/0$/,"/300")),"1"==is_limit_user&&m.call("hideOptionMenu"),
m.on("menu:share:appmessage",function(i){
var o=1,s=t(l,"1");
i&&"favorite"==i.scene&&(o=24,s=t(l,"4")),m.invoke("sendAppMessage",{
appid:d,
img_url:s,
img_width:"640",
img_height:"640",
link:e(c,o),
desc:u,
title:a
},function(){
n(c,fakeid,o);
});
}),m.on("menu:share:timeline",function(){
var i=l;
s.isIOS||(i=t(l,"2")),n(c,fakeid,2),m.invoke("shareTimeline",{
img_url:i,
img_width:"640",
img_height:"640",
link:e(c,2),
desc:u,
title:a
},function(){});
});
m.on("menu:share:weiboApp",function(){
m.invoke("shareWeiboApp",{
img_url:l,
link:e(c,3),
title:a
},function(){
n(c,fakeid,3);
});
}),m.on("menu:share:facebook",function(){
n(c,fakeid,4),m.invoke("shareFB",{
img_url:l,
img_width:"640",
img_height:"640",
link:e(c,4),
desc:u,
title:a
},function(){});
}),m.on("menu:share:QZone",function(){
var i=t(l,"6");
n(c,fakeid,5),m.invoke("shareQZone",{
img_url:i,
img_width:"640",
img_height:"640",
link:e(c,22),
desc:u,
title:a
},function(){});
}),m.on("menu:share:qq",function(){
var i=t(l,"7");
n(c,fakeid,5),m.invoke("shareQQ",{
img_url:i,
img_width:"640",
img_height:"640",
link:e(c,23),
desc:u,
title:a
},function(){});
}),m.on("menu:share:email",function(){
n(c,fakeid,5),m.invoke("sendEmail",{
content:e(c,5),
title:a
},function(){});
});
});define("biz_wap/utils/mmversion.js",[],function(){
"use strict";
function n(){
var n=/MicroMessenger\/([\d\.]+)/i,t=s.match(n);
return t&&t[1]?t[1]:!1;
}
function t(t,r,i){
var e=n();
if(e){
e=e.split("."),t=t.split("."),e.pop();
for(var o,s,u=f["cp"+r],c=0,a=Math.max(e.length,t.length);a>c;++c){
o=e[c]||0,s=t[c]||0,o=parseInt(o)||0,s=parseInt(s)||0;
var p=f.cp0(o,s);
if(!p)return u(o,s);
}
return i||0==r?!0:!1;
}
}
function r(n){
return t(n,0);
}
function i(n,r){
return t(n,1,r);
}
function e(n,r){
return t(n,-1,r);
}
function o(){
return u?"ios":a?"android":"unknown";
}
var s=navigator.userAgent,u=/(iPhone|iPad|iPod|iOS)/i.test(s),c=/Windows\sPhone/i.test(s),a=/(Android)/i.test(s),f={
"cp-1":function(n,t){
return t>n;
},
cp0:function(n,t){
return n==t;
},
cp1:function(n,t){
return n>t;
}
};
return{
get:n,
cpVersion:t,
eqVersion:r,
gtVersion:i,
ltVersion:e,
getPlatform:o,
isWp:c,
isIOS:u,
isAndroid:a
};
});define("appmsg/cdn_img_lib.js",[],function(){
"use strict";
String.prototype.http2https=function(){
return this.replace(/http:\/\/mmbiz\.qpic\.cn\//g,"https://mmbiz.qlogo.cn/");
},String.prototype.https2http=function(){
return this.replace(/https:\/\/mmbiz\.qlogo\.cn\//g,"http://mmbiz.qpic.cn/");
},String.prototype.isCDN=function(){
return 0==this.indexOf("http://mmbiz.qpic.cn/")||0==this.indexOf("https://mmbiz.qlogo.cn/");
};
});define("biz_common/utils/url/parse.js",[],function(){
"use strict";
function r(r){
var e=r.length,n=r.indexOf("?"),t=r.indexOf("#");
t=-1==t?e:t,n=-1==n?t:n;
var s=r.substr(0,n),a=r.substr(n+1,t-n-1),i=r.substr(t+1);
return{
host:s,
query_str:a,
hash:i
};
}
function e(e,n){
var t=r(e),s=t.query_str,a=[];
for(var i in n)n.hasOwnProperty(i)&&a.push(i+"="+encodeURIComponent(n[i]));
return a.length>0&&(s+=(""!=s?"&":"")+a.join("&")),t.host+(""!=s?"?"+s:"")+(""!=t.hash?"#"+t.hash:"");
}
function n(r,e,n,t){
r=r||location.href;
var s=r.indexOf("&"),a=r.length,i=r.replace(/^[\w\d]+:[\/\\]+/g,"").split("").reverse(),u=a-1-i.indexOf("/");
-1!=s&&-1==r.indexOf("?")&&s>u&&(r=r.replace("&","?"));
var o=new RegExp("([\\?&]"+e+"=)[^&#]*");
if(!r.match(o)){
var h=r.indexOf("?");
return-1==h?r+"?"+e+"="+n:h==r.length-1?r+e+"="+n:r+"&"+e+"="+n;
}
return t===!0?r.replace(o,"$1"+n):r;
}
return{
parseUrl:r,
join:e,
addParam:n
};
});define("biz_wap/utils/device.js",[],function(){
"use strict";
function s(s){
{
var e=s.match(/MQQBrowser\/(\d+\.\d+)/i),r=s.match(/QQ\/(\d+\.(\d+)\.(\d+)\.(\d+))/i)||s.match(/V1_AND_SQ_([\d\.]+)/),i=s.match(/MicroMessenger\/((\d+)\.(\d+))\.(\d+)/)||s.match(/MicroMessenger\/((\d+)\.(\d+))/),t=s.match(/Mac\sOS\sX\s(\d+\.\d+)/),n=s.match(/Windows(\s+\w+)?\s+?(\d+\.\d+)/),a=s.match(/MiuiBrowser\/(\d+\.\d+)/i),d=s.match(/MI-ONE/),h=s.match(/MI PAD/),w=s.match(/UCBrowser\/(\d+\.\d+(\.\d+\.\d+)?)/)||s.match(/\sUC\s/),c=s.match(/IEMobile(\/|\s+)(\d+\.\d+)/)||s.match(/WPDesktop/),b=s.match(/(ipod).*\s([\d_]+)/i),u=s.match(/(ipad).*\s([\d_]+)/i),p=s.match(/(iphone)\sos\s([\d_]+)/i),v=s.match(/Chrome\/(\d+\.\d+)/),m=s.match(/Mozilla.*Linux.*Android.*AppleWebKit.*Mobile Safari/),f=s.match(/(android)\s([\d\.]+)/i);
s.indexOf("HTC")>-1;
}
if(o.browser=o.browser||{},o.os=o.os||{},window.ActiveXObject){
var l=6;
(window.XMLHttpRequest||s.indexOf("MSIE 7.0")>-1)&&(l=7),(window.XDomainRequest||s.indexOf("Trident/4.0")>-1)&&(l=8),
s.indexOf("Trident/5.0")>-1&&(l=9),s.indexOf("Trident/6.0")>-1&&(l=10),o.browser.ie=!0,
o.browser.version=l;
}else s.indexOf("Trident/7.0")>-1&&(o.browser.ie=!0,o.browser.version=11);
f&&(this.os.android=!0,this.os.version=f[2]),b&&(this.os.ios=this.os.ipod=!0,this.os.version=b[2].replace(/_/g,".")),
u&&(this.os.ios=this.os.ipad=!0,this.os.version=u[2].replace(/_/g,".")),p&&(this.os.iphone=this.os.ios=!0,
this.os.version=p[2].replace(/_/g,".")),n&&(this.os.windows=!0,this.os.version=n[2]),
t&&(this.os.Mac=!0,this.os.version=t[1]),s.indexOf("lepad_hls")>0&&(this.os.LePad=!0),
h&&(this.os.MIPAD=!0),e&&(this.browser.MQQ=!0,this.browser.version=e[1]),r&&(this.browser.MQQClient=!0,
this.browser.version=r[1]),i&&(this.browser.WeChat=!0,this.browser.version=i[1]),
a&&(this.browser.MIUI=!0,this.browser.version=a[1]),w&&(this.browser.UC=!0,this.browser.version=w[1]||0/0),
c&&(this.browser.IEMobile=!0,this.browser.version=c[2]),m&&(this.browser.AndriodBrowser=!0),
d&&(this.browser.M1=!0),v&&(this.browser.Chrome=!0,this.browser.version=v[1]),this.os.windows&&(this.os.win64="undefined"!=typeof navigator.platform&&"win64"==navigator.platform.toLowerCase()?!0:!1);
var M={
iPad7:"iPad; CPU OS 7",
LePad:"lepad_hls",
XiaoMi:"MI-ONE",
SonyDTV:"SonyDTV",
SamSung:"SAMSUNG",
HTC:"HTC",
VIVO:"vivo"
};
for(var g in M)this.os[g]=-1!==s.indexOf(M[g]);
o.os.phone=o.os.phone||/windows phone/i.test(s),this.os.getNumVersion=function(){
return parseFloat(o.os.version,"10");
},this.os.hasTouch="ontouchstart"in window,this.os.hasTouch&&this.os.ios&&this.os.getNumVersion()<6&&(this.os.hasTouch=!1),
o.browser.WeChat&&o.browser.version<5&&(this.os.hasTouch=!1),o.browser.getNumVersion=function(){
return parseFloat(o.browser.version,"10");
},o.browser.isFFCanOcx=function(){
return o.browser.firefox&&o.browser.getNumVersion()>=3?!0:!1;
},o.browser.isCanOcx=function(){
return!(!o.os.windows||!o.browser.ie&&!o.browser.isFFCanOcx()&&!o.browser.webkit);
},o.browser.isNotIESupport=function(){
return!!o.os.windows&&(!!o.browser.webkit||o.browser.isFFCanOcx());
},o.userAgent={},o.userAgent.browserVersion=o.browser.version,o.userAgent.osVersion=o.os.version,
delete o.userAgent.version;
}
var o={};
s.call(o,window.navigator.userAgent);
var e=function(){
var s=window.navigator.userAgent,e=null;
if(o.os.android){
if(o.browser.MQQ&&o.browser.getNumVersion()>=4.2)return!0;
if(-1!=s.indexOf("MI2"))return!0;
if(o.os.version>="4"&&(e=s.match(/MicroMessenger\/((\d+)\.(\d+))\.(\d+)/))&&e[1]>=4.2)return!0;
if(o.os.version>="4.1")return!0;
}
return!1;
}(),r=function(){
var s=document.createElement("video");
if("function"==typeof s.canPlayType){
if("probably"==s.canPlayType('video/mp4; codecs="mp4v.20.8"'))return!0;
if("probably"==s.canPlayType('video/mp4; codecs="avc1.42E01E"')||"probably"==s.canPlayType('video/mp4; codecs="avc1.42E01E, mp4a.40.2"'))return!0;
}
return!1;
}();
return o.canSupportVideo=r||e,o.canSupportVideoMp4=r,o.canSupportH5Video=e,o;
});define("biz_wap/jsapi/a8key.js",["biz_wap/jsapi/core.js"],function(n){
"use strict";
var e,i=n("biz_wap/jsapi/core.js"),o=!1,t={},a=function(){
"undefined"!=typeof window.pass_ticket&&window.pass_ticket?(t.onAlreadyHasA8Key&&t.onAlreadyHasA8Key.call(A),
u()):0==window.isInWeixinApp()?(t.onOutOfWeixinApp&&t.onOutOfWeixinApp.call(A),u()):(o=1,
i.ready(c));
},c=function(){
window.isWeixinCached?w(u):(t.onNoCacheFuncWeixin&&t.onNoCacheFuncWeixin.call(A),
u());
},w=function(n){
if(t.onJSAPIGetA8KeyStart&&t.onJSAPIGetA8KeyStart.call(A),window.getA8KeyUrl)t.onJSAPIGetA8KeyEnd&&t.onJSAPIGetA8KeyEnd.call(A),
n(window.getA8KeyUrl);else{
var e=!1,o=setTimeout(function(){
e=!0,t.onJSAPIGetA8KeyTimeout&&t.onJSAPIGetA8KeyTimeout.call(A),n("");
},1500);
i.on("onGetA8KeyUrl",function(i){
o&&clearTimeout(o),e||(t.onJSAPIGetA8KeyEnd&&t.onJSAPIGetA8KeyEnd.call(A,i),n(i.url));
});
}
},u=function(n){
var i=!1;
if(n){
var o=getQueryFromURL(n);
window.uin=o.uin||window.uin,window.key=o.key||window.key,window.pass_ticket=o.pass_ticket||window.pass_ticket,
i=!0;
}
e&&e(i);
},A={
isPageCached:o
};
return A.config=function(n){
return t=n||{},A;
},A.onReady=function(n){
e=n,a();
},A;
});define("appmsg/index.js",["biz_wap/jsapi/a8key.js","biz_wap/utils/device.js","biz_common/utils/url/parse.js","appmsg/cdn_img_lib.js","biz_wap/utils/mmversion.js","appmsg/share.js","biz_common/log/jserr.js","biz_wap/ui/lazyload_img.js","appmsg/async.js","appmsg/pay_for_reading.js","appmsg/cache.js","appmsg/copyright_report.js","biz_common/dom/event.js","biz_wap/jsapi/core.js","appmsg/outer_link.js","appmsg/review_image.js","appmsg/iframe.js","appmsg/qqmusic.js","appmsg/voice.js","appmsg/wxtopic.js","appmsg/cdn_speed_report.js","appmsg/page_pos.js","appmsg/report_and_source.js","biz_common/dom/class.js","appmsg/report.js","biz_wap/safe/mutation_observer_report.js","sougou/index.js"],function(e){
"use strict";
function o(){
function o(e,o){
var t={
lossy:"UklGRiIAAABXRUJQVlA4IBYAAAAwAQCdASoBAAEADsD+JaQAA3AAAAAA",
lossless:"UklGRhoAAABXRUJQVlA4TA0AAAAvAAAAEAcQERGIiP4HAA==",
alpha:"UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==",
animation:"UklGRlIAAABXRUJQVlA4WAoAAAASAAAAAAAAAAAAQU5JTQYAAAD/////AABBTk1GJgAAAAAAAAAAAAAAAAAAAGQAAABWUDhMDQAAAC8AAAAQBxAREYiI/gcA"
},n=new Image;
n.onload=function(){
var t=n.width>0&&n.height>0;
o(e,t);
},n.onerror=function(){
o(e,!1);
},n.src="data:image/webp;base64,"+t[e];
}
var t=document.getElementsByTagName("body");
if(!t||!t[0])return!1;
t=t[0],function(){
var e=(new Date).getHours(),o=function(e,o){
o=o||"",window.isSg?(o=["uin:sougou","resp:"+o].join("|"),(new Image).src="/mp/jsreport?key="+e+"&content="+o+"&r="+Math.random()+"&from=sougou"):(o=["uin:"+top.window.user_uin,"resp:"+o].join("|"),
(new Image).src="/mp/jsreport?key="+e+"&content="+o+"&r="+Math.random());
},t=function(e,o,t){
var n=e+"_"+o;
t=t||1,window.logs.idkeys[n]||(window.logs.idkeys[n]={
val:0
}),window.logs.idkeys[n].val+=t;
},n=e>=11&&17>=e&&Math.random()<1,i=function(e,t){
n&&o(e,t);
};
window.__report=o,window.__commonVideoReport=i,window.__addIdKeyReport=t;
}();
var i=/^http(s)?:\/\/mp\.weixin\.qq\.com\//g;
try{
if(top!=window&&(!top||top&&top.location.href&&i.test(top.location.href))&&!window.isSg)throw new Error("in iframe");
}catch(r){
var s="",a=new Image;
a.src=("http://mp.weixin.qq.com/mp/jsreport?key=4&content=biz:"+biz+",mid:"+mid+",uin:"+uin+"[key4]"+s+"&r="+Math.random()).substr(0,1024);
}
window.isInWeixinApp()&&/#rd$/.test(location.href)&&!window.isWeixinCached&&location.replace(location.href.replace(/#rd$/,"#wechat_redirect"));
var c=e("biz_common/utils/url/parse.js");
e("appmsg/cdn_img_lib.js"),window.page_endtime=+new Date;
{
var p=e("biz_wap/utils/mmversion.js"),d=!p.isWp&&-1==navigator.userAgent.indexOf("MicroMessenger");
-1!=navigator.userAgent.indexOf("WindowsWechat");
}
if(e("appmsg/share.js"),window.isSg||"mp.weixin.qq.com"==location.host){
var m=e("biz_common/log/jserr.js");
m({
key:0,
reporturl:"http://mp.weixin.qq.com/mp/jsreport?1=1",
replaceStr:/http(s)?:(.*?)js\//g
});
}
window.logs.webplog={
lossy:0,
lossless:0,
alpha:0,
animation:0,
total:0
};
var l=-1!=navigator.userAgent.indexOf("TBS/"),w=function(e,t){
o(e,function(e,o){
if(window.logs.webplog[e]=o?1:0,window.logs.webplog.total++,4==window.logs.webplog.total){
var n=window.logs.webplog,i=Math.random();
l&&1>=i&&(n.lossy=n.lossless=n.alpha=1,window.logs.webplog=n);
var r=n.lossy&n.lossless&n.alpha;
t(!!r);
}
});
},g=function(e){
w("lossy",e),w("lossless",e),w("alpha",e),w("animation",e);
};
window.webp=!1,g(function(o){
window.webp=o,o&&window.localStorage&&window.localStorage.setItem&&window.localStorage.setItem("webp","1"),
window.logs.img={
download:{},
read:{},
load:{}
};
var t=document.getElementById("js_cover");
if(t){
var n=t.getAttribute("data-src");
if(n){
if(n.isCDN()){
var i=new Date;
for(i.setFullYear(2014,9,1);-1!=n.indexOf("?tp=webp");)n=n.replace("?tp=webp","");
for(;-1!=n.indexOf("&tp=webp");)n=n.replace("&tp=webp","");
1e3*ct>=i.getTime()&&""!=img_format&&"gif"!=img_format&&(n=n.replace(/\/0$/,"/640"),
n=n.replace(/\/0\?/,"/640?"),t.dataset&&(t.dataset.s="300,640")),o&&(n=c.addParam(n,"tp","webp",!0)),
n=c.addParam(n,"wxfrom","5",!0),is_https_res&&(n=n.http2https());
}
t.setAttribute("src",n),window.logs.img.read[n]=!0,window.logs.img.load[n]=!0,t.removeAttribute("data-src");
}
}
var r=e("biz_wap/ui/lazyload_img.js"),s=1;
window.logs.outer_pic=0,new r({
attrKey:"data-src",
lazyloadHeightWhenWifi:function(){
var e,o=1,t=1;
e=window.svr_time?new Date(1e3*window.svr_time):new Date;
var n=e.getHours();
return n>=20&&23>n&&(o=.5,t=0),{
bottom:o,
top:t
};
},
inImgRead:function(e){
e&&(window.logs.img.read[e]=!0);
},
changeSrc:function(e,o){
if(!o)return"";
for(var t=o;-1!=t.indexOf("?tp=webp");)t=t.replace("?tp=webp","");
for(;-1!=t.indexOf("&tp=webp");)t=t.replace("&tp=webp","");
if(o.isCDN())(e.dataset&&e.dataset.s||-1!=o.indexOf("wx_fmt=")&&-1==o.indexOf("wx_fmt=gif"))&&(t=t.replace(/\/0$/,"/640"),
t=t.replace(/\/0\?/,"/640?")),window.webp&&(t=c.addParam(t,"tp","webp",!0)),t=c.addParam(t,"wxfrom","5",!0),
is_https_res&&(t=t.http2https());else try{
var n=new RegExp("^http(s)?://((mmbiz.qpic.cn/.*)|(m.qpic.cn/.*)|(mmsns.qpic.cn/.*)|(shp.qpic.cn/.*)|(wx.qlogo.cn/.*)|(mmbiz.qlogo.cn/.*)|((a|b)[0-9]*.photo.store.qq.com/.*)|(mp.weixin.qq.com/.*)|(res.wx.qq.com/.*))");
n.test(o)||(window.__addIdKeyReport("28307",9),window.logs.outer_pic++);
}catch(i){}
var r=/^http\:\/\/(a|b)(\d)+\.photo\.store\.qq\.com/g;
return t=t.replace(r,"http://m.qpic.cn"),t=c.addParam(t,"wx_lazy","1",!0),window.logs.img.load[t]=!0,
t;
},
onerror:function(e,o){
var t=o?o.__retryload||0:0;
if(e&&!(t>s)&&(window.__addIdKeyReport("28307",4),window.__addIdKeyReport("28307",6+2*t),
s>t&&(t++,o.__retryload=t,o.src=c.addParam(e,"retryload",t,!0)),e.isCDN())){
var n=10;
/tp\=webp/.test(e)&&(n=11);
var i=new Image;
i.src="http://mp.weixin.qq.com/mp/jsreport?key="+n+"&content="+(encodeURIComponent(e)+"["+uin+"]")+"&r="+Math.random();
}
},
onload:function(e,o){
var t=o?o.__retryload||0:0;
t>s||(window.__addIdKeyReport("28307",3),window.__addIdKeyReport("28307",5+2*t));
},
detect:function(e){
if(e&&e.time&&e.loadList){
var o=e.time,t=e.loadList;
window.logs.img.download[o]=t;
}
},
container:document.getElementById("page-content")
});
}),e("appmsg/async.js"),window.isSg||(e("appmsg/pay_for_reading.js"),e("appmsg/cache.js"));
var u=e("appmsg/copyright_report.js"),f=e("biz_common/dom/event.js"),A=e("biz_wap/jsapi/core.js");
!function(){
var e=document.getElementById("post-user"),o=document.getElementById("copyright_info"),t=[];
if(e){
var n="57";
"26"==window.source&&(n="95"),"28"==window.source&&(n="96"),"29"==window.source&&(n="39"),
t.push({
dom:e,
username:user_name_new||user_name,
scene:n
});
}
o&&source_username&&t.push({
dom:o,
username:source_username,
profile_ext_signature:profile_ext_signature,
scene:"110"
});
for(var i=0,r=t.length;r>i;i++)!function(e){
f.on(e.dom,"click",function(){
if("copyright_info"==e.dom.id&&source_username){
u.card_click_report({
scene:"0"
});
var o="https://mp.weixin.qq.com/mp/profile_ext?action=home&username="+e.username+"&sn="+e.profile_ext_signature+"#wechat_redirect";
A.invoke("openUrlWithExtraWebview",{
url:o,
openType:1
},function(e){
-1==e.err_msg.indexOf("ok")&&(location.href=o);
});
}else A.invoke("profile",{
username:e.username,
scene:e.scene
},function(){
window.__addIdKeyReport("28307","1");
});
return!1;
}),p.isWp&&e.dom.setAttribute("href","weixin://profile/"+e.username);
}(t[i]);
}(),function(){
location.href.match(/fontScale=\d+/)&&p.isIOS&&A.on("menu:setfont",function(e){
e.fontScale<=0&&(e.fontScale=100),document.getElementsByTagName("html").item(0).style.webkitTextSizeAdjust=e.fontScale+"%",
document.getElementsByTagName("html").item(0).style.lineHeight=160/e.fontScale;
});
}();
var _=e("appmsg/outer_link.js");
if(new _({
container:document.getElementById("js_content"),
changeHref:function(e,o){
if(e&&0==e.indexOf("http://mp.weixin.qq.com/s"))e=e.replace(/#rd\s*$/,""),e=e.replace(/#wechat_redirect\s*$/,""),
e=e.replace(/[\?&]scene=21/,""),e+="&scene=21#wechat_redirect";else if(0!=e.indexOf("http://mp.weixinbridge.com/mp/wapredirect"))return"http://mp.weixinbridge.com/mp/wapredirect?url="+encodeURIComponent(e)+"&action=appmsg_redirect&uin="+uin+"&biz="+biz+"&mid="+mid+"&idx="+idx+"&type="+o+"&scene=0";
return e;
}
}),!d){
var h=e("appmsg/review_image.js"),y=document.getElementById("js_cover"),v=[];
y&&v.push(y),new h({
container:document.getElementById("js_content"),
is_https_res:is_https_res,
imgs:v
});
}
!function(){
try{
var e=document.getElementById("js_content");
if(!e||!e.querySelectorAll)return;
for(var o=e.querySelectorAll("*"),t="list-paddingleft-2,selectTdClass,noBorderTable,ue-table-interlace-color-single,ue-table-interlace-color-double".split(","),n=function(e){
if(e&&e.className){
for(var o=e.className.split(/\s+/),n=[],i=0,r=o.length;r>i;++i){
var s=o[i];
s&&-1!=t.indexOf(s)&&n.push(s);
}
e.className=n.join(" ");
}
},i=0,r=o.length;r>i;++i){
var s=o[i];
s.tagName&&"iframe"!=s.tagName.toLowerCase()&&n(s);
}
}catch(a){}
}(),window.fromWeixinCached||e("appmsg/iframe.js"),e("appmsg/qqmusic.js"),e("appmsg/voice.js"),
e("appmsg/wxtopic.js"),e("appmsg/cdn_speed_report.js"),e("appmsg/page_pos.js"),setTimeout(function(){
f.tap(document.getElementById("copyright_logo"),function(){
location.href="http://kf.qq.com/touch/sappfaq/150211YfyMVj150326iquI3e.html";
}),e("appmsg/report_and_source.js"),function(){
if(d){
var o=e("biz_common/dom/class.js");
o.addClass(t,"not_in_mm");
var n=document.createElement("link");
n.rel="stylesheet",n.type="text/css",n.async=!0,n.href=not_in_mm_css;
var i=document.getElementsByTagName("head")[0];
i.appendChild(n);
var r=document.getElementById("js_pc_qr_code_img");
if(r){
var s=10000004,a=document.referrer;
if(0==a.indexOf("http://weixin.sogou.com")?s=10000001:0==a.indexOf("https://wx.qq.com")&&(s=10000003),
window.isSg)r.setAttribute("src",sg_qr_code);else{
r.setAttribute("src","/mp/qrcode?scene="+s+"&size=102&__biz="+biz);
var c=new Image;
c.src="/mp/report?action=pcclick&__biz="+biz+"&uin="+uin+"&scene="+s+"&r="+Math.random();
}
document.getElementById("js_pc_qr_code").style.display="block";
}
var p=document.getElementById("js_profile_qrcode"),m=document.getElementById("js_profile_arrow_wrp"),l=document.getElementById("post-user");
if(p&&l&&m){
var w=function(){
var e=10000005,o=document.referrer;
0==o.indexOf("http://weixin.sogou.com")?e=10000006:0==o.indexOf("https://wx.qq.com")&&(e=10000007);
var t=document.getElementById("js_profile_qrcode_img");
if(t)if(window.isSg)t.setAttribute("src",sg_qr_code);else{
t.setAttribute("src","/mp/qrcode?scene="+e+"&size=102&__biz="+biz);
var n=new Image;
n.src="/mp/report?action=pcclick&__biz="+biz+"&uin="+uin+"&scene="+e+"&r="+Math.random();
}
return p.style.display="block",m.style.left=l.offsetLeft-p.offsetLeft+l.offsetWidth/2-8+"px",
!1;
};
f.on(l,"click",w),f.on(p,"click",w),f.on(document,"click",function(e){
var o=e.target||e.srcElement;
o!=l&&o!=p&&(p.style.display="none");
});
}
}else{
var g=document.getElementById("js_report_article3");
!!g&&(g.style.display="");
}
}(),function(){
var e=location.href.indexOf("scrolltodown")>-1?!0:!1,o=document.getElementById("img-content");
if(e&&o&&o.getBoundingClientRect){
var t=o.getBoundingClientRect().height;
window.scrollTo(0,t);
}
}(),e("appmsg/report.js");
for(var o=document.getElementsByTagName("map"),n=0,i=o.length;i>n;++n)o[n].parentNode.removeChild(o[n]);
if(u.card_pv_report(),Math.random()<.01)try{
var r="https://js.aq.qq.com/js/aq_common.js",s=document.createElement("script");
s.src=r;
var a=document.getElementsByTagName("head")[0];
a.appendChild(s);
}catch(c){}
},1e3),function(){
if(n.os.ios&&"onorientationchange"in window){
var e=[],o="onorientationchange"in window?"orientationchange":"resize",t=function(){
return 90===Math.abs(window.orientation)?1:2;
};
e.push({
ori:t(),
scroll:window.pageYOffset||document.documentElement.scrollTop,
istouchmove:!1
});
var i=(new Date).getHours();
f.on(window,o,function(){
var o=e.length-2,n=t();
if(o>=0){
var r=e[o],s=r.ori;
s!==n||e[e.length-1].istouchmove||(i>=11&&17>=i&&window.__report(63),window.scrollTo(0,r.scroll));
}
e.push({
ori:n,
scroll:window.pageYOffset||document.documentElement.scrollTop,
istouchmove:!1
});
}),f.on(window,"scroll",function(){
var o=e.length-1;
e[o].ori==t()&&(e[o].scroll=window.pageYOffset||document.documentElement.scrollTop,
e[o].istouchmove=!0);
});
}
}(),function(){
window.__observer&&window.__observer_data&&e("biz_wap/safe/mutation_observer_report.js");
}();
}
var t=e("biz_wap/jsapi/a8key.js"),n=e("biz_wap/utils/device.js");
t.config({
onOutOfWeixinApp:function(){
console.log("onOutOfWeixinApp");
},
onNoCacheFuncWeixin:function(){
console.log("isWeixinCached == false");
},
onAlreadyHasA8Key:function(){
console.log("URL已有A8Key");
},
onJSAPIGetA8KeyStart:function(){
console.log("onJSAPIGetA8KeyStart");
},
onJSAPIGetA8KeyEnd:function(){
console.log("onJSAPIGetA8KeyEnd");
},
onJSAPIGetA8KeyTimeout:function(){
console.log("onJSAPIGetA8KeyTimeout");
}
}),t.onReady(function(){
window.logs.pagetime.jsapi_ready_time=+new Date,window.logs.idkeys={},console.log("进入index.js init"),
o();
}),"undefined"!=typeof isSg&&e("sougou/index.js");
});