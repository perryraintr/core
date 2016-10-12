package com.raintr.pinshe.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeService {
	private String logo;
	private final int QRCOLOR = 0xFF000000; // 默认是黑色
	private final int BGWHITE = 0xFFFFFFFF; // 背景颜色

	public QRCodeService() {
	}

	public BufferedImage Get(String url) throws Exception {
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 0);
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);

		BufferedImage image = GetBufferedImage(url, BarcodeFormat.QR_CODE, 400, 400, hints);
		return AddLogo(image, new File(logo));
	}

	private BufferedImage AddLogo(BufferedImage image, File logoPic) throws IOException {
		Graphics2D g = null;
		BufferedImage logo = null;
		
		try {
			// 读取二维码图片，并构建绘图对象
			g = image.createGraphics();
			logo = ImageIO.read(logoPic);

			int widthLogo = logo.getWidth(null);
			int heightLogo = logo.getHeight(null);

			// logo放在中心
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - heightLogo) / 2;

			// logo放在右下角
			// int x = (image.getWidth() - widthLogo);
			// int y = (image.getHeight() - heightLogo);

			// 开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);

			// ImageIO.write(image, "png", new File("d:/sss.png"));

			return image;
		} finally {
			g.dispose();

			logo.flush();
			image.flush();
		}
	}

	private BufferedImage GetBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, Object> hints) throws WriterException {
		// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
		BitMatrix bm = new MultiFormatWriter().encode(content, barcodeFormat,
				width, height, hints);
		int w = bm.getWidth();
		int h = bm.getHeight();
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
			}
		}

		return image;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
