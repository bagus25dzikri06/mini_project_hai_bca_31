package com.simple_form.simple_form.service;

import com.simple_form.simple_form.AESPasswordEncryptorDecryptor;
import com.simple_form.simple_form.model.UsersModel;
import com.simple_form.simple_form.repository.UsersRepository;
import org.springframework.stereotype.*;

@Service
public class UsersService {
    
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public UsersModel registerUser(String login, String password, String email){
        if(login == null && password == null){
            return null;
        }else{
            final String secretKey = "secrete";

            AESPasswordEncryptorDecryptor aesPasswordEncryptorDecryptor = new AESPasswordEncryptorDecryptor();
            String encryptedPassword = aesPasswordEncryptorDecryptor.encrypt(password, secretKey);

            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setPassword(encryptedPassword);
            usersModel.setEmail(email);

            return usersRepository.save(usersModel);            
        }
    }

    public UsersModel authenticate(String login, String password){
        final String secretKey = "secrete";

        AESPasswordEncryptorDecryptor aesPasswordEncryptorDecryptor = new AESPasswordEncryptorDecryptor();
        String encryptedPassword = aesPasswordEncryptorDecryptor.encrypt(password, secretKey);

        return usersRepository.findByLoginAndPassword(login, encryptedPassword).orElse(null);
    }

}