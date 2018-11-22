package com.carozhu.fastdev.helper;

import com.carozhu.fastdev.priorityThreadPool.Priority;
import com.carozhu.fastdev.priorityThreadPool.PriorityRunnable;
import com.carozhu.fastdev.priorityThreadPool.PriorityThreadPoolExecutor;
import com.javaEmail.SendHelper;

import javax.mail.MessagingException;

public class EmailReportHelper {
    private static String sendEmail = "2419625609@qq.com";
    private static String sendEmailPasswd = "carolily520"; // 密码为且需要邮箱独立密码
    private static String recvEmail = "1025807062@qq.com";
    public static void doEmialReport(final String theme,final String content){
        PriorityRunnable runnable = new PriorityRunnable(Priority.NORMAL,() ->{
            try {
                SendHelper.sendEmail(theme,content,sendEmail,sendEmailPasswd,recvEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        PriorityThreadPoolExecutor.getInstance().execute(runnable);

        /*
        CachedThreadPool.getInstance().submit(() -> {
            try {
                SendHelper.sendEmail(theme,content,sendEmail,sendEmailPasswd,recvEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });*/
    }
}
