import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

/**
* @author by gimme on 2018/12/18.
*/
public class QRCodeGenerator {

	public static int getValidationCode(String noticeNo, BigDecimal fee) {
		String checkString = COLLECTION_CATEGORY + noticeNo + String.format("%010d", fee.intValue());
		int checksum = 0;
		for (int i = 0; i < checkString.length(); i++) {
			int shift = i%3+1;
			int multiplier = (1 << shift) - 1;
			int multiplicand = Character.getNumericValue(checkString.charAt(i));
			checksum += multiplicand * multiplier;
		}
		return checksum % 10;
	}
}
