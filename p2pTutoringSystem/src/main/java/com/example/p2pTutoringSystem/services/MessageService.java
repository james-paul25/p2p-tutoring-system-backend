package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.Utils.AESUtil;
import com.example.p2pTutoringSystem.Utils.PasswordUtils;
import com.example.p2pTutoringSystem.dto.MessageRequest;
import com.example.p2pTutoringSystem.entities.Messages;
import com.example.p2pTutoringSystem.entities.Session;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.Tutor;
import com.example.p2pTutoringSystem.repositories.MessageRepository;
import com.example.p2pTutoringSystem.repositories.SessionRepository;
import com.example.p2pTutoringSystem.repositories.StudentRepository;
import com.example.p2pTutoringSystem.repositories.TutorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {
    private final TutorRepository tutorRepository;
    private final MessageRepository messageRepository;
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;


    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "src/main/resources/static/uploads/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path fileDestination = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), fileDestination, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName;
    }

    public String sendMessage(long sessionId, long tutorId, long studentId, MessageRequest messageRequest) {
        try {
            Messages message = new Messages();

            Optional<Session> sessionOptional = sessionRepository.findBySessionId(sessionId);
            Optional<Tutor> tutorOptional = tutorRepository.findByTutorId(tutorId);
            Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);

            if (!sessionOptional.isPresent()) {
                return "Session not found";
            }
            if (!studentOptional.isPresent()) {
                return "Student not found";
            }
            if (!tutorOptional.isPresent()) {
                return "Tutor not found";
            }

            Student student = studentOptional.get();
            Tutor tutor = tutorOptional.get();
            Session session = sessionOptional.get();

            String hashMessage = AESUtil.encryptMessage(messageRequest.getMessage());

            message.setStudent(student);
            message.setTutor(tutor);
            message.setSession(session);
            message.setMessage(messageRequest.getMessage());
            message.setSenderRole(messageRequest.getSenderRole());

            if (messageRequest.getFile() != null && !messageRequest.getFile().isEmpty()) {
                String filePath = saveFile(messageRequest.getFile());
                message.setFileName(messageRequest.getFile().getOriginalFilename());
                message.setFilePath(filePath);
            }

            messageRepository.save(message);
            return "Message sent successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send message";
        }
    }

    public String sendMessage(Long sessionId, Long tutorId, Long studentId, MultipartFile file, String senderRole, String sendAt) {
        try {
            Messages message = new Messages();

            Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
            Optional<Tutor> tutorOptional = tutorRepository.findById(tutorId);
            Optional<Student> studentOptional = studentRepository.findById(studentId);

            if (!sessionOptional.isPresent()) {
                return "Session not found";
            }
            if (!studentOptional.isPresent()) {
                return "Student not found";
            }
            if (!tutorOptional.isPresent()) {
                return "Tutor not found";
            }

            Student student = studentOptional.get();
            Tutor tutor = tutorOptional.get();
            Session session = sessionOptional.get();

            message.setStudent(student);
            message.setTutor(tutor);
            message.setSession(session);
            message.setSenderRole(senderRole);


            String filePath = "";
            if (file != null && !file.isEmpty()) {
                filePath = saveFile(file);
                //String encryptFilePath = AESUtil.encryptMessage(filePath);
                //String encryptFileName = AESUtil.encryptMessage(file.getOriginalFilename());
                message.setFileName(file.getOriginalFilename());
                message.setFilePath(filePath);
            }

            messageRepository.save(message);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send message";
        }
    }

    public List<Messages> getMessagesBySession(long sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) {
            return new ArrayList<>();
        }

        List<Messages> messages = messageRepository.findBySession_SessionIdOrderBySendAtAsc(sessionId);

        /*
        for (Messages message : messages) {
            String encrypted = message.getMessage();
            String filePathEncrypted = message.getFilePath();
            String fileNameEncrypted = message.getFileName();

            System.out.println("Message: " + encrypted);
            System.out.println("Message FilePath: " + filePathEncrypted);
            System.out.println("Message FileName: " + fileNameEncrypted);
            if (encrypted != null) {
                String decrypted = AESUtil.decryptMessage(encrypted);
                String filePathDecrypted = AESUtil.decryptMessage(filePathEncrypted);
                String fileNameDecrypted = AESUtil.decryptMessage(fileNameEncrypted);
                message.setFileName(fileNameDecrypted);
                message.setFilePath(filePathDecrypted);
                message.setMessage(decrypted);
            } else {
                message.setMessage(null);
                message.setFilePath(null);
                message.setMessage(null);
            }
        }

         */

        return messages;
    }

}
