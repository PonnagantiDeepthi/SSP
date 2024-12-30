package ug.daes.onboarding.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ug.daes.DAESService;
import ug.daes.Result;
import ug.daes.onboarding.constant.ApiResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class AppUtil {

	
	private static UUID uuid;
	
	public static String getUUId() {
		uuid = UUID.randomUUID();
		return uuid.toString();
	}

    //to check pass and conf pass are same or not
    public static boolean checkPassword(String pass, String confPass) {
        if (pass.equals(confPass))
            return true;
        return false;
    }

    //to getcurrent date
    public static Date getCurrentDate() {
        return new Date();
    }


    //to get expiry of token
    public static boolean getExpiryDate(Date date) {
        if (date.before(getCurrentDate())) {

            return true;
        }

        return false;
    }

    //this method is temporary we need to implement role based later
    public static String getPrivileges() {

        return "user";
    }


    public static Date setExpiryDate() {
        //1440000 means 24 hrs
        //60 * 1000 means one min
        return new Date(new Date().getTime() + 1440000 * 7);
    }

    public static ApiResponse createApiResponse(boolean success, String msg, Object object) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(msg);
        apiResponse.setResult(object);
        apiResponse.setSuccess(success);
        return apiResponse;

    }

    public static Timestamp getTimeStamp() {
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        return ts;
    }

    public static String getUnameFromDecryptedRefreshToken(String token) {
        String[] parts = token.split("#");
        //String refToken = parts[0]; // 004
        String uname = parts[1]; // 034556
        return uname;
    }

    public static String genrateHashCode(Object obj) throws NoSuchAlgorithmException {
        int sha256 = obj.hashCode();
        String i = Integer.toString(sha256);
        return i;
    }

    public static String getHashCode(Object o)
            throws NoSuchAlgorithmException {
        String base64hash = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(o.toString().getBytes());
            byte[] digest = md.digest();

            base64hash = Base64.getEncoder().encodeToString(digest);
           // System.out.println("hashcode ===> " + base64hash);
        } catch (Exception e) {

        }

        return base64hash;
    }


    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    public static String formatFilterDate(Date startDate, Date endDate) {
        String startDateStr = formatDate(startDate);
        String endDateStr = formatDate(endDate);
        System.out.println("start " + startDateStr);
        System.out.println("end " + endDateStr);
        if (startDateStr.equals(endDateStr)) {
            return startDateStr;
        } else {
            return startDateStr + " to " + endDateStr;
        }
    }

    public static String getDirNameForSameFilter(String fileName) {
        char[] path = fileName.toCharArray();
        String reportByDatePath;
        if (path[path.length - 5] == ')') {
            int val = Integer.parseInt(path[path.length - 7] + "" + path[path.length - 6]) + 1;
            if (val<10){
                reportByDatePath=fileName.replaceAll("\\(.*?\\)","(0" + val + ")");
            }else {
                reportByDatePath=fileName.replaceAll("\\(.*?\\)","(" + val + ")");
            }


        } else {

            reportByDatePath = fileName + "(01)";
        }
        return reportByDatePath;
    }
    
    public static String getZipPathJson(List<String> paths){
        ObjectMapper mapper = new ObjectMapper();
        String newJsonData = null;
        try {
            newJsonData = mapper.writeValueAsString(paths);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return newJsonData;
    }

    public static List<String> getListFromPathJson(String zipPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> paths = null;
        try {
            if (zipPath!=null && zipPath!=""){
                paths = objectMapper.readValue(zipPath, List.class);
            }else {
                paths = new ArrayList<>();
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
        return paths;
    }

    public static int findPosInArray(char[] a, char target){
        return IntStream.range(0, a.length)
                .filter(i -> target == a[i])
                .findFirst()
                .orElse(-1);	// return -1 if target is not found
    }

    public static String generateRefrenceNo(String packageRefNumber) {
        if (packageRefNumber!=null){
            return UUID.nameUUIDFromBytes(packageRefNumber.getBytes()).toString().toUpperCase();
        }else {
            return UUID.randomUUID().toString().toUpperCase();
        }

    }

    public static String getBase64FromByteArr(byte[] bytes){
        Base64.Encoder base64 = Base64.getEncoder();
        return base64.encodeToString(bytes);
    }

    public static Map getCustomerDetailsHM(String customerDtlsJson) throws IOException{


        try {
            Map<String, String> map = new ObjectMapper().readValue(customerDtlsJson,
                    new TypeReference<Map<String, String>>() {});
            return map;
        } catch (JsonProcessingException e) {
            e.printStackTrace(); 
        }
        return null;
    }

    public static String getSDFDate(Date creationDate) {
        String pattern = "YYYY-MM-dd hh:mm:ss.s";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(creationDate);

        return date;
    }
    
    public static byte[] getSalt(String salt) {
		return salt.getBytes();
	}
	
	public static String encrypt(String plainText) {
		String secretKey = "DiGiTaLtRuStTeChNoLoGy";
		try {
			byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			IvParameterSpec ivspec = new IvParameterSpec(iv);
//			PBKDF2WithHmacSHA256
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), getSalt(plainText), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
	
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}
	
	public static String getDate(){
    	SimpleDateFormat smpdate = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Date date = new Date();
		return smpdate.format(date);
    }
	
	public static String getRaDate() throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZ");
		Date date = new Date();
		return formatter.format(date).toString();
    }
	
	public static String getTimeStamping() throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return f.format(new Date());
	}
	
	
	public static double getDifferenceInSeconds(Date startDate, Date endDate){
        long diff = endDate.getTime()-startDate.getTime();
        return diff/1000.0;
    }
	
	
	public static String getTimeStampString(Date date) throws ParseException {
        Optional<Date> oDate = Optional.ofNullable(date);
        Date d = oDate.isPresent() ? oDate.get() : new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return f.format(d);
    }
	
	
	public static String getTotalTime(String t1,String t2) throws ParseException {
		
		SimpleDateFormat smpdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date time1 = smpdate.parse(t1);
		
		//System.out.println("ReqStartTime ::" + time1);
		
		SimpleDateFormat smpdate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date time2 = smpdate2.parse(t2);
		
		//System.out.println("CompliationTime ::" + time2);
		
		 long difference_In_Time = time2.getTime() - time1.getTime();
		 
		 
		 
        //System.out.println("difference_In_Time milisec :: " + difference_In_Time);
//		 long difference_In_Seconds= (difference_In_Time/ 1000)% 60;
//		System.out.println("difference_In_Seconds ::" + difference_In_Seconds);
		 
		 String strLong = Double.toString(difference_In_Time/1000.0);
		 
		return strLong;
		
	}
	
	public static String encryptedString(String s) {
		try {
			// System.out.println("s => " + s);
			Result result = DAESService.encryptData(s);
			return new String(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
