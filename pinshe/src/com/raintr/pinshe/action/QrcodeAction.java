package com.raintr.pinshe.action;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.service.QRCodeService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping(value = "/")
public class QrcodeAction extends BaseAction {	
	@Autowired
	private QRCodeService qRCodeService;
	
	@RequestMapping(value = "/qrcode")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}

	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setContentType("image/jpeg");
		String url = request.getParameter("url");
		
		BufferedImage image = qRCodeService.Get(url);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());  
        encoder.encode(image);
		return null;
	}
}  

