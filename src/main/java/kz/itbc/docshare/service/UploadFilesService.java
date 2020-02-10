package kz.itbc.docshare.service;

import com.google.gson.Gson;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;

public class UploadFilesService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        final HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        int id_Folder = Integer.parseInt(session.getAttribute(OPENED_FOLDER_ATTRIBUTE).toString());
        FileDAO fileDAO = new FileDAO();
        File file = null;
        HashMap<String, String> params = new HashMap<>();
        Map<String, File> files = new HashMap<>();
        Map<String, String> message = new HashMap<>();

        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator iter = upload.getItemIterator(req);
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    String getFieldName = item.getFieldName();
                    InputStream stream = item.openStream();
                    String getName = item.getName();
                    if (item.isFormField()) {
                        String value = Streams.asString(item.openStream());
                        if (getFieldName.contains("file_flagHidden")) {
                            String number = getFieldName.replace("file_flagHidden", "");
                            params.put("file" + number, value);
                        }
                    } else {
                        if (!item.isFormField()) {
                            file = new File();
                            file.setName(getName);
                            byte[] bytes = IOUtils.toByteArray(stream);
                            file.setFileData(bytes);
                            file.setFlagHidden(false);
                            file.setId_FileType(getFileType(item));
                            file.setId_Folder(id_Folder);
                            file.setUserCreated(user);
                            files.put(getName, file);
                        }
                    }
                }

                try{
                    for (Map.Entry<String, File> entry : files.entrySet()) {
                        try {
                            if (params.containsValue(entry.getValue().getName())) {
                                entry.getValue().setFlagHidden(true);
                            }
                            fileDAO.addFile(entry.getValue().getName(), entry.getValue().getFileData(), entry.getValue().isFlagHidden(),
                                    false, entry.getValue().getId_FileType(), entry.getValue().getId_Folder(), user);
                        }
                        catch (FileDAOException e) {
                            SERVICE_LOGGER.error("Ошибка добавления файла", e);// exception handling
                            message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                        }
                    }
                    message.put(MESSAGE_ATTRIBUTE, "Вы успешно загрузили файлы");
                } catch (Exception e) {
                    message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                }

                res.setContentType(JSON_CONTENT_TYPE);
                res.setCharacterEncoding(UTF_8_CHARSET);
                PrintWriter writer = res.getWriter();
                String jsonResponse = new Gson().toJson(message);
                writer.write(jsonResponse);
            } catch (FileUploadException e) {
                message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                SERVICE_LOGGER.error("Возникла ошибка", e);// exception handling
            } catch (IOException e) {
                message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                SERVICE_LOGGER.error("Возникла ошибка", e);// exception handling
            }
        }
    }

    private String getFileName(FileItemStream p) {
        return Paths.get(p.getName()).getFileName().toString();
    }
    private int getFileType(FileItemStream part) {
        int file_type_id;
        String fileName = getFileName(part).toLowerCase().trim();
        if (fileName.endsWith(".pdf")) {
            file_type_id = 1;
        } else if (fileName.endsWith(".doc")) {
            file_type_id = 2;
        } else if (fileName.endsWith(".docx")) {
            file_type_id = 3;
        } else if (fileName.endsWith(".rar")) {
            file_type_id = 6;
        } else if (fileName.endsWith(".zip")) {
            file_type_id = 7;
        } else if (fileName.endsWith(".txt")) {
            file_type_id = 8;
        } else if (fileName.endsWith(".xlsx")) {
            file_type_id = 5;
        } else if (fileName.endsWith(".ppt")) {
            file_type_id = 9;
        } else if (fileName.endsWith(".pptx")) {
            file_type_id = 10;
        }  else if (fileName.endsWith(".xls")) {
            file_type_id = 4;
        } else if (fileName.endsWith(".png")) {
            file_type_id = 11;
        } else if (fileName.endsWith(".jpg")) {
            file_type_id = 12;
        } else {
            file_type_id = 13;
        }
        return file_type_id;
    }
}
