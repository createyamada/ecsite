package jp.co.mgsystems.yuricollection.gootscatalog.commons;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class CommonAdvice {
    /*
     * リクエストの空白文字をnullに変換する共通処理
     */
    @InitBinder
    public void InitBinder(WebDataBinder bilBinder) {
        bilBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
}
