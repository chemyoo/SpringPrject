package com.chemyoo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class SmbFileUtils {
	
	private SmbFileUtils() throws NoSuchMethodException {
		throw new NoSuchMethodException("SmbFileUtils can not Instanse");
	}
	
	/**
     * 共享文件夹配置
     */
    private static final String HOST = "127.0.0.1";
    private static final String USER_NAME = "Administrator";
    private static final String PASSWORD = "chemyoo";
    private static final String PATH = "/java/";
    private static final String REMOTE_URL = "smb://" + USER_NAME + ":" + PASSWORD + "@" + HOST + PATH;
	
	/**
	  * 读取共享文件夹下的所有文件(文件夹)的名称
	  * @param remoteUrl
	  */
	public static void getSharedFileList() {
	    SmbFile smbFile;
	    try {
	        // smb://userName:passWord@host/path/
	        smbFile = new SmbFile(REMOTE_URL);
	        if (!smbFile.exists()) {
	            System.out.println("no such folder");
	        } else {
	            SmbFile[] files = smbFile.listFiles();
	            for (SmbFile f : files) {
	                System.out.println(f.getName());
	            }
	        }
	    } catch (MalformedURLException | SmbException e) {
	        e.printStackTrace();
	    } 
	}
	
	/**
	 * 创建文件夹
	 * @param remoteUrl 
	 * @param folderName
	 * @return
	 */
	public static void smbMkDir(String remoteUrl, String folderName) {
	    SmbFile smbFile;
	    try {
	        // smb://userName:passWord@host/path/folderName
	        smbFile = new SmbFile(remoteUrl + folderName);
	        if (!smbFile.exists()) {
	            smbFile.mkdir();
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (SmbException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	  * 上传文件
	  * @param remoteUrl
	  * @param shareFolderPath
	  * @param localFilePath
	  * @param fileName
	  */
	public static void uploadFileToSharedFolder(String remoteUrl, String shareFolderPath, String localFilePath, String fileName) {
	    InputStream inputStream = null;
	    OutputStream outputStream = null;
	    try {
	        File localFile = new File(localFilePath);
	        inputStream = new FileInputStream(localFile);
	        // smb://userName:passWord@host/path/shareFolderPath/fileName
	        SmbFile smbFile = new SmbFile(remoteUrl + shareFolderPath + "/" + fileName);
	        smbFile.connect();
	        outputStream = new SmbFileOutputStream(smbFile);
	        byte[] buffer = new byte[4096];
	        int len = 0; // 读取长度
	        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
	            outputStream.write(buffer, 0, len);
	        }
	        // 刷新缓冲的输出流
	        outputStream.flush();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            outputStream.close();
	            inputStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	/**
	  * 下载文件到浏览器
	  * @param httpServletResponse
	  * @param remoteUrl
	  * @param shareFolderPath
	  * @param fileName
	  */
	public static void downloadFileToBrowser(HttpServletResponse httpServletResponse, String remoteUrl, String shareFolderPath, String fileName) {
	    SmbFile smbFile;
	    SmbFileInputStream smbFileInputStream = null;
	    OutputStream outputStream = null;
	    try {
	        // smb://userName:passWord@host/path/shareFolderPath/fileName
	        smbFile = new SmbFile(remoteUrl + shareFolderPath + "/" + fileName);
	        smbFileInputStream = new SmbFileInputStream(smbFile);
	        httpServletResponse.setHeader("content-type", "application/octet-stream");
	        httpServletResponse.setContentType("application/vnd.ms-excel;charset=UTF-8");
	        httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName);
	        // 处理空格转为加号的问题
	        httpServletResponse.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));
	        outputStream = httpServletResponse.getOutputStream();
	        byte[] buff = new byte[2048];
	        int len;
	        while ((len = smbFileInputStream.read(buff)) != -1) {
	            outputStream.write(buff, 0, len);
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (SmbException e) {
	        e.printStackTrace();
	    } catch (UnknownHostException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    finally {
	        try {
	            outputStream.close();
	            smbFileInputStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	  * 下载文件到指定文件夹
	  * @param remoteUrl
	  * @param shareFolderPath
	  * @param fileName
	  * @param localDir
	  */
	public static void downloadFileToFolder(String remoteUrl, String shareFolderPath, String fileName, String localDir) {
	    InputStream in = null;
	    OutputStream out = null;
	    try {
	        SmbFile remoteFile = new SmbFile(remoteUrl + shareFolderPath + File.separator + fileName);
	        File localFile = new File(localDir + File.separator + fileName);
	        in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
	        out = new BufferedOutputStream(new FileOutputStream(localFile));
	        byte[] buffer = new byte[1024];
	        while (in.read(buffer) != -1) {
	            out.write(buffer);
	            buffer = new byte[1024];
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            out.close();
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	  * 删除文件
	  * @param remoteUrl
	  * @param shareFolderPath
	  * @param fileName
	  */
	public static void deleteFile(String remoteUrl, String shareFolderPath, String fileName) {
	    SmbFile SmbFile;
	    try {
	        // smb://userName:passWord@host/path/shareFolderPath/fileName
	        SmbFile = new SmbFile(remoteUrl + shareFolderPath + "/" + fileName);
	        if (SmbFile.exists()) {
	            SmbFile.delete();
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (SmbException e) {
	    e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		SmbFileUtils.getSharedFileList();
	}
	
}
