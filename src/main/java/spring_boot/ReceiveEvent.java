package spring_boot;

/**
 * erp��Ʒ����������webhook�ص�ʾ��
 */
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ReceiveEvent {
	
	//����д���Լ���ҵ��token
	@Value("${erp.eidToken}")
	private String erpToken ;
	
	
	@RequestMapping("/webhookEventErp")
	public String webhookEventErp(@RequestHeader Map<String, String> header, @RequestParam(required=false) String eid,@RequestParam(required=false) String eventType,
	@RequestParam(required=false) String eventId,@RequestParam(required=false) String createTime) {
		String contentBody = "eid=" + eid+ "&eventType=" + eventType + "&eventId=" + eventId+"&createTime="+createTime;
		System.out.println(contentBody);
		Map<String,String> paramsMap = new TreeMap<String,String>();
		paramsMap.put("eid", eid);
		paramsMap.put("eventType", eventType);
		paramsMap.put("eventId", eventId);
		paramsMap.put("createTime", createTime);
		contentBody = mapToString(paramsMap);
		if(WebHookUtil.checkAuth(erpToken, contentBody, header)){
			System.out.println("���յ�һ���Ϸ����ͣ�����Ϊ�� "+contentBody);
			//�������͵��߼�д������,�첽���������2S��ʱ
			//.....
		}else{
			System.out.println("���յ�һ���Ƿ�����");
			return "not ok";
		}
		
		return "ok";
	}
	
	// ��key�ֶ�˳��������װk1=v1&k2=v2��ʽ
	private String mapToString(Map<String, String> map) {
	    StringBuilder sb = new StringBuilder();
	    Set<String> keys = map.keySet();
	    for (String key : keys) {
	        sb.append(key).append("=").append(map.get(key)).append("&");
	    }
	    if (sb.length() > 0) {
	        return sb.substring(0, sb.length() - 1);
	    } else {
	        return sb.toString();
	    }
	}

	public static void main(String[] args) {
		SpringApplication.run(ReceiveEvent.class, args);
	}
}
