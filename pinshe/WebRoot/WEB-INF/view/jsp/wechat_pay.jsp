<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript">
		function payCall(){
			WeixinJSBridge.invoke('getBrandWCPayRequest', {${data}}, function(res){
				//WeixinJSBridge.log(res.err_msg);
				//alert(res.err_code + " " + res.err_desc + " " + res.err_msg);
				if(res.err_msg == "get_brand_wcpay_request:ok"){
					//location.href = "http://www.pinshe.org/html/v1/coffee/coupon_share.html?from=${from}&id=${id}&orderno=${orderno}";
					location.href = "${url}";
				}
			});
		}
		
		if(typeof WeixinJSBridge == "undefined"){
			if(document.addEventListener){
				document.addEventListener('WeixinJSBridgeReady', payCall, false);
			}else{
				if(document.attachEvent){
					document.attachEvent('WeixinJSBridgeReady', payCall);
					document.attachEvent('onWeixinJSBridgeReady', payCall);
				}
			}
		}else{
			payCall();
		}
    </script>
  </head>
</html>