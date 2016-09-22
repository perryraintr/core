define("a/card.js",["biz_common/dom/event.js","biz_common/utils/report.js","appmsg/a_report.js","biz_wap/utils/ajax.js","biz_wap/utils/position.js","biz_wap/jsapi/core.js","biz_wap/jsapi/cardticket.js"],function(t,i,e,o){
"use strict";
function a(t,i){
n("http://mp.weixin.qq.com/mp/ad_report?action=follow&type="+t+i.report_param);
}
function r(t){
var i=t.adData,e=t.pos_type||0,o=i.tid,n=i.type,s=i.url,d=i.rl,j={};
t.report_param=t.report_param||"";
var l=t.btn;
if(l){
p.on(l,"click",function(p){
if(!j[o]){
j[o]=!0;
var l,u,m=!!p&&p.target;
m&&(l=_.getX(m,"js_ad_link extra_link")+p.offsetX,u=_.getY(m,"js_ad_link extra_link")+p.offsetY),
c({
type:n,
report_type:2,
click_pos:0,
url:encodeURIComponent(s),
tid:o,
rl:encodeURIComponent(d),
__biz:biz,
pos_type:e,
pt:105,
pos_x:l,
pos_y:u
},function(){
j[o]=!1,a(37,t),r.openCardDetail(i.card_id,i.card_ext,t);
});
}
return!1;
});
}
}
var p=t("biz_common/dom/event.js"),n=t("biz_common/utils/report.js"),s=t("appmsg/a_report.js"),c=s.AdClickReport,_=(t("biz_wap/utils/ajax.js"),
t("biz_wap/utils/position.js")),d=(t("biz_wap/jsapi/core.js"),t("biz_wap/jsapi/cardticket.js"));
return r.openCardDetail=function(t,i,e){
d.openCardDetail({
card_id:t,
card_ext:i,
success:function(){
!!e&&a(38,e);
},
error:function(){
!!e&&a(39,e),o("调起卡券错误");
},
access_denied:function(){
!!e&&a(40,e),o("异常错误[access_denied]");
}
});
},r;
});define("biz_wap/utils/position.js",[],function(){
"use strict";
function t(e,f){
var n=e.offsetLeft;
return e.offsetParent.className!=f&&(n+=t(e.offsetParent,f)),n;
}
function e(t,f){
var n=t.offsetTop;
return t.offsetParent.className!=f&&(n+=e(t.offsetParent,f)),n;
}
return{
getX:t,
getY:e
};
});define("appmsg/a_report.js",["biz_wap/utils/ajax.js","biz_wap/jsapi/core.js"],function(t){
"use strict";
function o(t,o){
var i="https:"==top.location.protocol?1500:1200,p="/mp/advertisement_report?r="+Math.random()+"&",s=[],e=!1;
for(var c in t)t.hasOwnProperty(c)&&s.push(c+"="+t[c]);
p+=s.join("&"),r({
url:p,
mayAbort:!0,
type:"GET",
success:function(){
a&&a(56+n);
},
error:function(){
a&&a(57+n);
},
complete:function(){
e||(e=!0,!!o&&o());
},
async:!0
}),setTimeout(function(){
e||(e=!0,window.__ajaxtest="1",!!o&&o());
},i);
}
{
var r=t("biz_wap/utils/ajax.js"),a=window.__report,i=top.location.protocol,n="https:"==i?5:0;
t("biz_wap/jsapi/core.js");
}
return{
AdClickReport:o
};
});define("biz_common/utils/respTypes.js",[],function(require,exports,module){
"use strict";
var logList=[],log=function(r){
logList.push(r);
},printLog=function(){
for(var r=0,e=logList.length;e>r;++r)console.log("[RespType]"+logList[r]);
},isArray=function(r){
return"[object Array]"==Object.prototype.toString.call(r);
},getValueType=function(r){
return isArray(r)?"array":typeof r;
},parseRtDesc=function(r,e){
var t="mix",o=!1,c=e;
if(e){
var n="_R",s=e.indexOf(n),i=e.length-n.length;
o=-1!=s&&s==i,c=o?e.substring(0,i):e;
}
return"string"==typeof r?t=r:isArray(r)?t="array":"object"==typeof r&&(t="object"),
{
key:c,
type:t,
isRequired:o
};
},checkForArrayRtDesc=function(r,e){
if(!isArray(r))return!1;
for(var t=0,o=r.length;o>t;++t){
for(var c,n=r[t],s=0,i=!1;c=e[s++];)if(checkForRtDesc(n,c)){
i=!0;
break;
}
if(!i&&s>0)return!1;
}
return!0;
},checkForStringRtDesc=function(r,e){
var t=getValueType(r),o=parseRtDesc(e),c=o.type==t;
return c||log("miss match type : "+t+" !== "+o.type),c;
},checkForObjectRtDesc=function(r,e){
if("object"!=typeof r||isArray(r))return log("must be object"),!1;
var t=r,o=r;
for(var c in e)if(e.hasOwnProperty(c)){
var n=e[c],s=parseRtDesc(n,c),i=s.key;
o=t[i];
var u=getValueType(o);
if(s.isRequired&&void 0===o)return log("is required @key="+i),!1;
if(void 0!==o){
if(u!=s.type&&"mix"!=s.type)return log("miss match type : "+u+" !== "+s.type+" @key="+i),
!1;
if(("array"==u||"object"==u)&&"mix"!=s.type&&!checkForRtDesc(o,n))return!1;
}
}
return!0;
},checkForRtDesc=function(r,e){
return isArray(e)?checkForArrayRtDesc(r,e):"object"==typeof e?checkForObjectRtDesc(r,e):"string"==typeof e?checkForStringRtDesc(r,e):!1;
},check=function(json,rtDescs){
if("string"==typeof json)try{
json=eval("("+json+")");
}catch(e){
return log("parse json error"),!1;
}
if("object"!=typeof json)return log("must be object"),!1;
isArray(rtDesc)||(rtDescs=[rtDescs]);
for(var rtDesc,i=0;rtDesc=rtDescs[i++];)if(checkForRtDesc(json,rtDesc))return!0;
return!1;
};
return{
check:function(r,e){
logList=[];
try{
var t=check(r,e);
return t||printLog(),t;
}catch(o){
return logList.push("[rtException]"+o.toString()),printLog(),!1;
}
},
getMsg:function(){
return logList.join(";");
}
};
});define("appmsg/cmt_tpl.html.js",[],function(){
return'<li class="discuss_item" id="cid<# if (is_from_me == 1) { #><#=my_id#><# } else { #><#=content_id#><# } #>">\n    <# if(is_elected == 1){ #>\n    <div class="discuss_opr">\n        <span class="media_tool_meta tips_global meta_praise js_comment_praise <# if(like_status == 1){ #>praised<# } #>" data-status="<#=like_status#>" data-content-id=\'<#=content_id#>\'>\n            <i class="icon_praise_gray"></i>\n            <span class="praise_num"><# if(like_num_format !== 0){ #><#=like_num_format#> <# } #></span>\n        </span>\n    </div>\n    <# } #>\n    <div class="user_info">\n        <strong class="nickname"><#=nick_name#><# if(is_from_friend == 1){ #>(朋友)<# } #></strong>\n        <img class="avatar" src="<#=logo_url#>">\n    </div>\n    <div class="discuss_message">\n        <span class="discuss_status"><#=status#></span>\n        <div class="discuss_message_content">\n        <#=content#>\n        </div>\n    </div>\n    <p class="discuss_extra_info">\n        <#=time#>               \n        <# if (is_from_me == 1) { #>\n        <a class="discuss_del js_del" href="javascript:;" data-my-id="<#=my_id#>" data-content-id="<#=content_id#>">删除</a>\n        <# } #>\n    </p>\n    <# if(reply && reply.reply_list && reply.reply_list.length > 0){ #>\n        <div class="reply_result">\n            <div class="nickname">作者回复</div>\n            <div class="discuss_message">\n                <div class="discuss_message_content">\n                <#=reply.reply_list[0].content#>\n                </div>\n            </div>\n            <p class="discuss_extra_info"><#=reply.reply_list[0].time#></p>\n        </div>\n    <# } #>\n        \n</li>';
});define("sougou/a_tpl.html.js",[],function(){
return'<h3 class="rich_media_area_title">相关文章</h3>\n<ul class="relate_article_list">\n    <# for(var i in list){#>\n    <li class="relate_article_item">\n        <a class="relate_article_link sg_link" href="<#=list[i].url#>" target="_blank"><#=list[i].title#></a>\n    </li>\n    <#}#>\n</ul>\n';
});define("appmsg/emotion/emotion.js",["appmsg/emotion/dom.js","appmsg/emotion/slide.js","appmsg/emotion/common.js","appmsg/emotion/nav.js","appmsg/emotion/textarea.js","appmsg/emotion/map.js"],function(t,n){
"use strict";
function i(){
var t={};
j.each(b,function(n,i){
t[n]=i+1;
}),b=t;
}
function e(){
w.WIDTH=h=j("#js_article").width()||j("#js_cmt_mine").width(),w.pageCount=_=o(),
a(),s(),r();
}
function o(){
d=h-2*N,v=parseInt(d/k),f=3*v-1;
var t=parseInt(M/f);
return M%f!==0&&t++,t;
}
function a(){
var t=j("#js_slide_wrapper"),n=w.wrapperWidth=_*h;
t.css({
width:n+"px"
});
}
function s(){
for(var t=j("#js_slide_wrapper").el[0],n=(h-v*k)/2,i=0,e=_;e>i;i++){
var o=document.createElement("ul");
o.setAttribute("class","emotion_list"),t.appendChild(o),j(o).css({
width:h+"px",
"float":"left",
"padding-left":n+"px",
"padding-right":"0"
}),c(o,i);
}
}
function r(){
for(var t=j("#js_navbar"),n=0,i=_;i>n;n++){
var e=j(j.el("li"));
e.attr("class","emotion_nav js_emotion_nav"),T.push(e),t.append(e);
}
w.navs=T;
}
function c(t){
for(var n=0,i=f;i>n;n++){
var e=document.createElement("li");
if(S++,S>M)break;
e=p(S),j(t).append(e);
}
var o=m();
j(t).append(o);
}
function p(t){
var n=j(j.el("li")),i=j(j.el("i")),e=27===t?-1:1;
i.attr("class","icon_emotion icon"+t),i.css({
"background-position":(1-t)*C-e+"px -1px"
}),n.attr("class","emotion_item js_emotion_item"),n.attr("data-index",t);
var o=k+"px";
return n.css({
width:o,
height:o
}),n.append(i),n;
}
function m(){
var t=j(j.el("li")),n=j(j.el("i"));
t.attr("class","emotion_item del js_emotion_item"),t.attr("data-index",-1),n.attr("class","icon_emotion del");
var i=k+"px";
return t.css({
width:i,
height:i
}),t.append(n),t;
}
function l(){
function t(){
o.show(),O.show(),e.blur(),j.later(function(){
e.blur();
});
}
function n(){
o.hide(),O.hide(),e.focus(),j.later(function(){
e.focus();
});
}
O=j("#js_emotion_panel");
var i=j("#js_cmt_input"),e=i.el[0],o=j("#js_emotion_panel_arrow_wrp");
O.hide(),j("#js_emotion_switch").on("tap",function(i){
i.preventDefault(),i.stopPropagation(),E=!E,E?t():n();
}),i.on("tap",function(){
O.hide(),E=!1;
});
}
function u(){
function t(t){
if(!w.isMoved){
var n=j(t.currentTarget),i=+n.attr("data-index");
I.inputEmotion(i);
}
}
j("li.js_emotion_item").on("click",t),j("li.js_emotion_item").on("touchend",t);
}
var d,_,f,v,h,j=t("appmsg/emotion/dom.js"),g=t("appmsg/emotion/slide.js"),w=t("appmsg/emotion/common.js"),x=t("appmsg/emotion/nav.js"),I=t("appmsg/emotion/textarea.js"),n=(j.each,
{}),E=!1,O=null,b=t("appmsg/emotion/map.js"),T=[],N=15,M=w.EMOTIONS_COUNT,k=w.EMOTION_LI_SIZE,C=w.EMOTION_SIZE;
n.init=function(){
l(),e(),g.init(),x.activeNav(0),u(),I.init(),i();
};
var S=0;
return n.encode=function(t){
var n=/\/([\u4e00-\u9fa5\w]{1,3})/g,i=t.match(n);
return i?(j.each(i,function(n){
var i=n.replace("/",""),e=[i.slice(0,1),i.slice(0,2),i.slice(0,3)];
j.each(e,function(n){
if(void 0!==b[n]){
var i=b[n],e='<i class="icon_emotion_single icon'+i+'"></i>';
t=t.replace("/"+n,e);
}
});
}),t):t;
},n.hidePannel=function(){
O.hide();
},n;
});define("biz_common/utils/report.js",[],function(){
"use strict";
return function(n){
var e=new Image;
e.src=n;
};
});define("biz_common/utils/huatuo.js",[],function(){
"use strict";
var t=window.performance&&window.performance.timing,n="http://report.huatuo.qq.com/report.cgi?appid=10065&speedparams=",o=t?["unloadEventStart","unloadEventEnd","redirectStart","redirectEnd","fetchStart","domainLookupStart","domainLookupEnd","connectStart","connectEnd","requestStart","responseStart","responseEnd","domLoading","domInteractive","domContentLoadedEventStart","domContentLoadedEventEnd","domComplete","loadEventStart","loadEventEnd"]:[],e={
points:[],
setFlags:function(n,r,a){
e.points=["flag1="+n,"flag2="+r,"flag3="+a];
for(var d=0;d<o.length;++d)e.points.push(d+1+"="+t[o[d]]);
},
setPoint:function(t,n){
return this.points.push(t+"="+n),this;
},
report:function(){
var t=this.points.join("&");
t=encodeURIComponent(t),(new Image).src=n+t,e.points=[];
}
};
return e;
});define("biz_common/utils/cookie.js",[],function(){
"use strict";
var e={
get:function(e){
if(""==e)return"";
var t=new RegExp(e+"=([^;]*)"),n=document.cookie.match(t);
return n&&n[1]||"";
},
set:function(e,t,n){
var o=new Date;
return o.setDate(o.getDate()+(n||1)),n=o.toGMTString(),document.cookie=e+"="+t+";expires="+n,
!0;
}
};
return e;
});define("pages/voice_component.js",["biz_common/dom/event.js","biz_common/tmpl.js","pages/loadscript.js","pages/music_player.js","biz_common/dom/class.js","pages/report.js"],function(t,o,e,i){
"use strict";
function r(t){
this._o={
type:0,
comment_id:"",
src:"",
mid:"",
songId:"",
autoPlay:!1,
duration:0,
debug:!1,
needVioceMutex:!0,
appPlay:!0,
title:"",
singer:"",
epname:"",
coverImgUrl:"",
webUrl:[location.protocol,"//mp.weixin.qq.com/s?referFrom=#referFrom#&songid=#songId#&__biz=",window.biz,"&mid=",window.mid,"&idx=",window.idx,"&sn=",window.sn,"#wechat_redirect"].join(""),
playingCss:"",
playCssDom:"",
playArea:"",
progress:"",
detailUrl:"",
detailArea:""
},this._init(t);
}
function n(t,o,e,i){
m.num++,o.musicSupport=m.musicSupport,o.show_not_support=!1,m.musicSupport||1!=m.num||(o.show_not_support=!0);
var r=document.createElement("div"),n="";
n=i?_.render(t,o):_.tmpl(t,o),r.innerHTML=n;
var p=e.parentNode;
p&&(p.lastChild===e?p.appendChild(r.children[0]):p.insertBefore(r.children[0],e.nextSibling));
}
function p(){
"undefined"==typeof window.reportVoiceid&&(window.reportVoiceid=[]),"undefined"==typeof window.reportMid&&(window.reportMid=[]);
}
function s(){
u.on(window,"unload",a);
}
function a(){
for(var t in m.reportData)l.musicreport({
data:m.reportData[t]
});
}
function c(t){
var o="//open.music.qq.com/fcgi-bin/fcg_music_get_song_info_weixin.fcg?song_id=#songid#&mid=#mid#&format=json&app_id=100311669&app_key=55d6cdaee6fb3a41275a48067f8d7638&device_id=weixin&file_type=mp3&qqmusic_fromtag=50&callback=get_song_info_back";
o=o.replace("#mid#",t.mid).replace("#songid#",t.id),y({
url:o,
timeout:3e4,
callbackName:"get_song_info_back",
callback:function(o){
if(!o||"undefined"==typeof o.ret)return void("function"==typeof t.onError&&t.onError({
errcode:1
}));
var e=1;
1001==o.ret?e=0:1002==o.ret?e=2:1003==o.ret?e=3:1004==o.ret&&(e=4),t.onSuc({
status:e
});
},
onerror:function(o){
var e=4;
switch(1*o){
case 400:
e=2;
break;

case 500:
e=3;
break;

default:
e=4;
}
"function"==typeof t.onError&&t.onError({
errcode:e
});
}
});
}
function d(t){
return new r(t);
}
var u=t("biz_common/dom/event.js"),_=t("biz_common/tmpl.js"),y=t("pages/loadscript.js"),h=t("pages/music_player.js"),g=t("biz_common/dom/class.js"),l=t("pages/report.js"),m={
musicSupport:h.getSurportType(),
reportData:{},
posIndex:{},
qqMusiceSongId:"http://thirdparty.gtimg.com/#songId#.m4a?fromtag=38&songid=#songId#",
qqMusiceMid:"http://thirdparty.gtimg.com/C100#mid#.m4a?fromtag=38&songid=#songId#",
num:0
};
return p(),s(),r.prototype._init=function(t){
this._extend(t),this._g={
copyright:-1,
check_copyright:!1
},this._initSrc(),this._initQQmusicLyric(),this._initReportData(),this._initPlayer(),
this._playEvent();
},r.prototype._initSrc=function(){
var t=this._o;
t.src||(0==t.type||1==t.type)&&(t.mid?t.src=m.qqMusiceMid.replace("#mid#",t.mid).replace(/#songId#/g,t.songId||""):t.songId&&(t.src=m.qqMusiceSongId.replace(/#songId#/g,t.songId||"")));
},r.prototype._initQQmusicLyric=function(){
var t=this._o;
t.webUrl=0==t.type||1==t.type?t.webUrl.replace("#songId#",t.songId||"").replace("#referFrom#","music.qq.com"):t.webUrl.replace("#songId#","").replace("#referFrom#","");
},r.prototype._initReportData=function(){
var t=this._o;
2==t.type||3==t.type?window.reportVoiceid.push(t.songId):(0==t.type||1==t.type)&&window.reportMid.push(t.songId),
"undefined"==typeof m.reportData[t.type]&&(m.reportData[t.type]=l.getMusicReportData(t),
m.posIndex[t.type]=0),this._g.posIndex=m.posIndex[t.type]++;
var o=m.reportData[t.type];
o.musicid.push(t.songId),o.commentid.push(t.comment_id),o.hasended.push(0),o.mtitle.push(t.title),
o.detail_click.push(0),o.duration.push(parseInt(1e3*t.duration)),o.errorcode.push(0),
o.play_duration.push(0);
},r.prototype._initPlayer=function(){
m.musicSupport&&(this._o.onStatusChange=this._statusChangeCallBack(),this._o.onTimeupdate=this._timeupdateCallBack(),
this._o.onError=this._errorCallBack(),this.player=new h.init(this._o));
},r.prototype._playEvent=function(){
var t=this,o=this._o,e=this._g;
if(m.musicSupport){
var i=0;
2==o.type||3==o.type?i=3:(0==o.type||1==o.type)&&(i=1),u.tap(o.playArea,function(){
return g.hasClass(o.playCssDom,o.playingCss)?(t.player.stop(),l.report({
type:i,
comment_id:o.comment_id,
voiceid:o.songId,
action:5
})):3==i?t._playMusic(3):1==i&&t._checkCopyright(function(){
t._playMusic(1);
}),!1;
});
}
o.detailUrl&&o.detailArea&&u.tap(o.detailArea,function(){
t._checkCopyright(function(){
m.reportData[o.type].detail_click[e.posIndex]=1,window.location.href=o.detailUrl;
});
});
},r.prototype._checkCopyright=function(t){
var o=this,e=this._o,i=this._g;
return 1*i.copyright===1&&"function"==typeof t?void t():void(this._musicCopyrightWarnning()!==!0&&(i.check_copyright||(i.check_copyright=!0,
c({
id:e.songId,
mid:e.mid,
onSuc:function(e){
return i.check_copyright=!1,i.copyright=1*e.status,1==i.copyright?void("function"==typeof t&&t()):void(o._musicCopyrightWarnning(!0)===!0);
},
onError:function(t){
i.check_copyright=!1,o.player.monitor(1==t.errcode?"copyright_cgi_err":2==t.errcode?"copyright_net_err":3==t.errcode?"copyright_timeout":"copyright_other_err");
}
}))));
},r.prototype._musicCopyrightWarnning=function(t){
var o=this._g;
return 1*o.copyright===0?(i("该歌曲版权已过期，无法播放"),t===!0&&this.player.monitor("no_copyright"),
!0):1*o.copyright===2?(i("抱歉，应版权方要求，当前国家或地区暂不提供此歌曲服务"),t===!0&&this.player.monitor("overseas"),
!0):1*o.copyright===3?(i("该歌曲版权已过期，无法播放"),t===!0&&this.player.monitor("fee"),!0):1*o.copyright===4?(i("抱歉，歌曲信息不正确"),
t===!0&&this.player.monitor("musicid_error"),!0):!1;
},r.prototype._playMusic=function(t){
var o=this._o,e=this._g;
this.player.play(0),m.reportData[o.type].hasended[e.posIndex]=1,l.report({
type:t,
comment_id:o.comment_id,
voiceid:o.songId,
action:4
});
},r.prototype._extend=function(t){
for(var o in t)this._o[o]=t[o];
},r.prototype._statusChangeCallBack=function(){
var t=this;
return function(o,e){
t._updatePlayerCss(this,e);
};
},r.prototype._timeupdateCallBack=function(){
var t=this,o=this._o,e=this._g;
return function(i,r){
t._updateProgress(this,r),0!=r&&(m.reportData[o.type].play_duration[e.posIndex]=parseInt(1e3*r));
};
},r.prototype._errorCallBack=function(){
var t=this,o=this._o,e=this._g;
return function(i,r){
m.reportData[o.type].errorcode[e.posIndex]=r,t._updatePlayerCss(this,3);
};
},r.prototype._updatePlayerCss=function(t,o){
var e=this._o,i=e.playCssDom,r=e.progress;
2==o||3==o?(g.removeClass(i,e.playingCss),!!r&&(r.style.width=0)):1==o&&g.addClass(i,e.playingCss);
},r.prototype._updateProgress=function(t,o){
var e=this._o,i=e.progress,r=t.getDuration();
r&&i&&(i.style.width=this._countProgress(r,o));
},r.prototype._countProgress=function(t,o){
return o/t*100+"%";
},{
init:d,
renderPlayer:n
};
});define("new_video/ctl.js",["biz_wap/utils/ajax.js"],function(i){
"use strict";
var e=top.window.user_uin,t=Math.floor(top.window.user_uin/100)%20;
e||(t=-1);
var o=function(){
return t>=0;
};
top.window.__webviewid||(top.window.__webviewid=+new Date+"_"+Math.ceil(1e3*Math.random()));
var d=function(){
var i=top.window.mid,t=top.window.idx,o="";
o=i&&t?i+"_"+t:"";
var d=top.window.__webviewid,r=[e,o,d].join("_");
return r;
},r=function(e){
if(20>t)try{
var r=e.vid||"",w={};
w.__biz=top.window.biz||"",w.vid=r,w.clienttime=+new Date;
var n=top.window.mid,a=top.window.idx,p="";
n&&a?(w.type=1,p=n+"_"+a):(w.type=2,p=r),w.id=p,w.webviewid=d(),w.step=e.step||0,
w.orderid=e.orderid||0,w.ad_source=e.ad_source||0,w.traceid=e.traceid||0,w.ext1=e.ext1||"",
w.ext2=e.ext2||"",w.r=Math.random(),w.devicetype=top.window.devicetype,w.version=top.window.clientversion,
w.is_gray=o()?1:0;
var _=i("biz_wap/utils/ajax.js");
_({
url:"/mp/ad_video_report?action=user_action",
type:"post",
data:w
});
}catch(v){}
};
return{
report:r,
getWebviewid:d,
showAd:o
};
});define("biz_common/utils/monitor.js",[],function(){
"use strict";
var n=[],i={};
return i.setAvg=function(t,e,u){
return n.push(t+"_"+e+"_"+u),n.push(t+"_"+(e-1)+"_1"),i;
},i.setSum=function(t,e,u){
return n.push(t+"_"+e+"_"+u),i;
},i.send=function(){
if(0!=n.length){
var i=new Image;
i.src="//mp.weixin.qq.com/mp/jsmonitor?idkey="+n.join(";"),n=[];
}
},i;
});define("biz_common/utils/spin.js",[],function(){
"use strict";
function t(t,i){
var e,o=document.createElement(t||"div");
for(e in i)o[e]=i[e];
return o;
}
function i(t){
for(var i=1,e=arguments.length;e>i;i++)t.appendChild(arguments[i]);
return t;
}
function e(t,i,e,o){
var n=["opacity",i,~~(100*t),e,o].join("-"),r=.01+e/o*100,s=Math.max(1-(1-t)/i*(100-r),t),a=c.substring(0,c.indexOf("Animation")).toLowerCase(),l=a&&"-"+a+"-"||"";
return u[n]||(p.insertRule("@"+l+"keyframes "+n+"{0%{opacity:"+s+"}"+r+"%{opacity:"+t+"}"+(r+.01)+"%{opacity:1}"+(r+i)%100+"%{opacity:"+t+"}100%{opacity:"+s+"}}",p.cssRules.length),
u[n]=1),n;
}
function o(t,i){
var e,o,n=t.style;
for(i=i.charAt(0).toUpperCase()+i.slice(1),o=0;o<d.length;o++)if(e=d[o]+i,void 0!==n[e])return e;
return void 0!==n[i]?i:void 0;
}
function n(t,i){
for(var e in i)t.style[o(t,e)||e]=i[e];
return t;
}
function r(t){
for(var i=1;i<arguments.length;i++){
var e=arguments[i];
for(var o in e)void 0===t[o]&&(t[o]=e[o]);
}
return t;
}
function s(t,i){
return"string"==typeof t?t:t[i%t.length];
}
function a(t){
this.opts=r(t||{},a.defaults,f);
}
function l(){
function e(i,e){
return t("<"+i+' xmlns="urn:schemas-microsoft.com:vml" class="spin-vml">',e);
}
p.addRule(".spin-vml","behavior:url(#default#VML)"),a.prototype.lines=function(t,o){
function r(){
return n(e("group",{
coordsize:d+" "+d,
coordorigin:-c+" "+-c
}),{
width:d,
height:d
});
}
function a(t,a,l){
i(p,i(n(r(),{
rotation:360/o.lines*t+"deg",
left:~~a
}),i(n(e("roundrect",{
arcsize:o.corners
}),{
width:c,
height:o.width,
left:o.radius,
top:-o.width>>1,
filter:l
}),e("fill",{
color:s(o.color,t),
opacity:o.opacity
}),e("stroke",{
opacity:0
}))));
}
var l,c=o.length+o.width,d=2*c,u=2*-(o.width+o.length)+"px",p=n(r(),{
position:"absolute",
top:u,
left:u
});
if(o.shadow)for(l=1;l<=o.lines;l++)a(l,-2,"progid:DXImageTransform.Microsoft.Blur(pixelradius=2,makeshadow=1,shadowopacity=.3)");
for(l=1;l<=o.lines;l++)a(l);
return i(t,p);
},a.prototype.opacity=function(t,i,e,o){
var n=t.firstChild;
o=o.shadow&&o.lines||0,n&&i+o<n.childNodes.length&&(n=n.childNodes[i+o],n=n&&n.firstChild,
n=n&&n.firstChild,n&&(n.opacity=e));
};
}
var c,d=["webkit","Moz","ms","O"],u={},p=function(){
var e=t("style",{
type:"text/css"
});
return i(document.getElementsByTagName("head")[0],e),e.sheet||e.styleSheet;
}(),f={
lines:12,
length:7,
width:5,
radius:10,
rotate:0,
corners:1,
color:"#000",
direction:1,
speed:1,
trail:100,
opacity:.25,
fps:20,
zIndex:2e9,
className:"spinner",
top:"50%",
left:"50%",
position:"absolute"
};
a.defaults={},r(a.prototype,{
spin:function(i){
this.stop();
var e=this,o=e.opts,r=e.el=n(t(0,{
className:o.className
}),{
position:o.position,
width:0,
zIndex:o.zIndex
});
if(n(r,{
left:o.left,
top:o.top
}),i&&i.insertBefore(r,i.firstChild||null),r.setAttribute("role","progressbar"),
e.lines(r,e.opts),!c){
var s,a=0,l=(o.lines-1)*(1-o.direction)/2,d=o.fps,u=d/o.speed,p=(1-o.opacity)/(u*o.trail/100),f=u/o.lines;
!function h(){
a++;
for(var t=0;t<o.lines;t++)s=Math.max(1-(a+(o.lines-t)*f)%u*p,o.opacity),e.opacity(r,t*o.direction+l,s,o);
e.timeout=e.el&&setTimeout(h,~~(1e3/d));
}();
}
return e;
},
stop:function(){
var t=this.el;
return t&&(clearTimeout(this.timeout),t.parentNode&&t.parentNode.removeChild(t),
this.el=void 0),this;
},
lines:function(o,r){
function a(i,e){
return n(t(),{
position:"absolute",
width:r.length+r.width+"px",
height:r.width+"px",
background:i,
boxShadow:e,
transformOrigin:"left",
transform:"rotate("+~~(360/r.lines*d+r.rotate)+"deg) translate("+r.radius+"px,0)",
borderRadius:(r.corners*r.width>>1)+"px"
});
}
for(var l,d=0,u=(r.lines-1)*(1-r.direction)/2;d<r.lines;d++)l=n(t(),{
position:"absolute",
top:1+~(r.width/2)+"px",
transform:r.hwaccel?"translate3d(0,0,0)":"",
opacity:r.opacity,
animation:c&&e(r.opacity,r.trail,u+d*r.direction,r.lines)+" "+1/r.speed+"s linear infinite"
}),r.shadow&&i(l,n(a("#000","0 0 4px #000"),{
top:"2px"
})),i(o,i(l,a(s(r.color,d),"0 0 1px rgba(0,0,0,.1)")));
return o;
},
opacity:function(t,i,e){
i<t.childNodes.length&&(t.childNodes[i].style.opacity=e);
}
});
var h=n(t("group"),{
behavior:"url(#default#VML)"
});
return!o(h,"transform")&&h.adj?l():c=o(h,"animation"),a;
});define("biz_wap/jsapi/pay.js",["biz_wap/jsapi/core.js"],function(e){
"use strict";
var a=e("biz_wap/jsapi/core.js"),s={
getLatest:function(e){
a.invoke("getLatestAddress",{
appId:e.appId,
scope:e.scope||"jsapi_address",
signType:e.signType||"sha1",
addrSign:e.addrSign||"mphardcodeaddrSign",
timeStamp:e.timeStamp||"",
nonceStr:e.nonceStr||""
},function(a){
return a.err_msg&&"system:function_not_exist"==a.err_msg?void(e.error&&e.error()):void(e.callback&&e.callback(a));
});
},
edit:function(e){
a.invoke("editAddress",{
appId:e.appId,
scope:e.scope||"jsapi_address",
signType:e.signType||"sha1",
addrSign:e.addrSign||"mphardcodeaddrSign",
timeStamp:e.timeStamp||"",
nonceStr:e.nonceStr||""
},function(a){
e.callback&&e.callback(a);
});
}
},n=function(e){
a.invoke("getBrandWCPayRequest",{
appId:e.app_id,
timeStamp:e.time_stamp,
nonceStr:e.nonce_str,
"package":e.package,
signType:e.sign_type||"SHA1",
paySign:e.pay_sign
},function(a){
"get_brand_wcpay_request:ok"==a.err_msg?e.success&&e.success(a):e.error&&e.error(a.err_msg);
});
};
return{
pay:n,
address:s
};
});define("appmsg/reward_entry.js",["biz_common/dom/event.js","biz_wap/utils/ajax.js","biz_wap/jsapi/core.js"],function(e,n,t,r){
"use strict";
function a(e){
e&&(e.style.display="block");
}
function o(e){
e&&(e.style.display="none");
}
function i(e){
var n=window.innerWidth||document.documentElement.innerWidth,t=(Math.ceil((p-188)/42)+1)*Math.floor((n-15)/42);
c="http://mp.weixin.qq.com/mp/reward?act=getrewardheads&__biz="+biz+"&appmsgid="+mid+"&idx="+idx+"&sn="+sn+"&offset=0&count="+t+"&source=1#wechat_redirect";
var i="#wechat_redirect",d="https://mp.weixin.qq.com/bizmall/reward?__biz="+biz+"&appmsgid="+mid+"&idx="+idx+"&sn="+sn+"&timestamp="+e.timestamp+"&showwxpaytitle=1"+i,w=document.getElementById("js_reward_link");
w&&(_.on("activity:state_change",function(e){
if("onResume"==e.state_change||"onResume"==e.state){
var n=(new Date).valueOf();
if(-1!=navigator.userAgent.indexOf("Android")&&localStorage.getItem("lastOnresumeTime")&&n-parseInt(localStorage.getItem("lastOnresumeTime"))<=h)return;
localStorage.setItem("lastOnresumeTime",n),_.invoke("setNavigationBarColor",{
actionCode:"1"
}),location.reload();
}
}),m.on(w,"tap",function(e){
return"0"==window.wx_user_can_reward?void r("你已成为该公众号的黑名单用户，暂时无法赞赏。"):(e.preventDefault(),
void _.invoke("openUrlWithExtraWebview",{
url:d
},function(e){
e.err_msg.indexOf(":ok")>-1||(location.href=d);
}));
})),g=e.reward_head_imgs;
var u=l();
f.reward&&1==e.can_reward?(a(f.reward),m.on(window,"load",function(){
m.on(window,"scroll",s);
})):o(f.reward);
var v=document.getElementById("js_reward_inner");
v&&u>0&&a(v);
var b=document.getElementById("js_reward_total");
b&&(b.innerText=e.reward_total,b.setAttribute("href",c));
}
function d(e,n){
var t=document.createElement("span");
t.className="reward_user_avatar";
var r=new Image;
return r.onload=function(){
window.logs&&window.logs.reward_heads_total++,r.onload=r.onerror=null;
},r.onerror=function(){
window.logs&&window.logs.reward_heads_total++,window.logs&&window.logs.reward_heads_fail++,
r.onload=r.onerror=null;
},r.src=n,t.appendChild(r),e.appendChild(t),t;
}
function l(){
if(g.length){
var e=document.getElementById("js_reward_list"),n=0,t=document.createDocumentFragment();
if(e){
for(var r=0,a=g.length;a>r&&(n++,d(t,g[r]),n!=3*w);++r);
n>w&&(e.className+=" tl"),e.innerHTML="",e.appendChild(t);
}
return n;
}
}
function s(){
var e=window.pageYOffset||document.documentElement.scrollTop;
e+p>f.reward.offsetTop&&(u({
type:"GET",
url:"/bizmall/reward?act=report&__biz="+biz+"&appmsgid="+mid+"&idx="+idx,
async:!0
}),m.off(window,"scroll",s),s=null);
}
var w,c,m=e("biz_common/dom/event.js"),u=e("biz_wap/utils/ajax.js"),_=e("biz_wap/jsapi/core.js"),p=window.innerHeight||document.documentElement.clientHeight,f={
reward:document.getElementById("js_reward_area")
},g=[],h=500;
return window.logs&&(window.logs.reward_heads_total=0,window.logs.reward_heads_fail=0),
{
handle:function(e,n){
w=n,i(e);
},
render:function(e){
w=e,l();
}
};
});