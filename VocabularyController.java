package idv.gizone.studio.controller;

import idv.gizone.eco.controller.GenericController;
import idv.gizone.studio.core.dao.WordDetail;
import idv.gizone.studio.core.dto.WordInfo;
import idv.gizone.studio.core.service.WordService;
import idv.gizone.utils.web.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static idv.gizone.studio.controller.APIManager.GOOGLE_TTS_API;

@Controller
public class VocabularyController extends GenericController {

    @RequestMapping(path={APIManager.WORD_VOICE+"/{word}"}, method = {RequestMethod.GET})
    public ResponseEntity<byte[]> textToSpeech(@PathVariable String word) throws Exception
    {
        word= URLEncoder.encode(word, "UTF-8");
        URL url = new URL(String.format(GOOGLE_TTS_API, word));
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.addRequestProperty("User-Agent", "Mozilla/4.76");
        InputStream audioSrc = urlConn.getInputStream();
        DataInputStream read = new DataInputStream(audioSrc);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = read.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);

    }
}
