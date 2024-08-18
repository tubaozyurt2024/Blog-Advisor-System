package com.patika.bloghubservice.service;

import com.patika.bloghubservice.client.email.dto.request.EmailCreateRequest;
import com.patika.bloghubservice.client.email.dto.request.enums.EmailTemplate;
import com.patika.bloghubservice.client.email.service.EmailClientService;
import com.patika.bloghubservice.client.payment.dto.request.PaymentRequest;
import com.patika.bloghubservice.client.payment.service.PaymentClientService;
import com.patika.bloghubservice.converter.UserConverter;
import com.patika.bloghubservice.dto.SendMEmailMessage;
import com.patika.bloghubservice.dto.request.UserSaveRequest;
import com.patika.bloghubservice.dto.response.UserResponse;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.producer.RabbitMqService;
import com.patika.bloghubservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EmailClientService emailClientService;
    private final PaymentClientService paymentClientService;
    private final RabbitMqService rabbitMqService;

    public UserResponse saveUser(UserSaveRequest request) {

        if (request.getEmail() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
            throw new BlogHubException(ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            throw new BlogHubException(ExceptionMessages.USER_ALREADY_DEFINED);
        } else {
            User savedUser = new User(request.getEmail(), request.getPassword()); // ödev password' hash'le

            userRepository.save(savedUser);
            //emailClientService.sendEmail(new EmailCreateRequest(request.getEmail(), EmailTemplate.CREATE_USER_TEMPLATE));
            //paymentClientService.createPayment(new PaymentRequest(15.90,request.getEmail()));

            rabbitMqService.senEmail(new SendMEmailMessage(request.getEmail(),EmailTemplate.CREATE_USER_TEMPLATE));

            return UserConverter.toResponse(savedUser);
        }

    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));

        return UserConverter.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserConverter.toResponse(users);
    }
    public List<UserResponse> getAllUsersByStatus(StatusType statusType) {
        List<User> users = userRepository.findAllByStatus(statusType);
        return UserConverter.toResponse(users);
    }

    public void changeStatus(String email, StatusType statusType) {
        Optional<User> foundUser = userRepository.findByEmail(email);

        foundUser.get().setStatusType(statusType);


        //userRepository.changeStatus(email, foundUser.get());
    }

    public void changeStatusBulk(List<String> emailList, StatusType statusType) {
        emailList.forEach(email -> changeStatus(email, statusType));
    }

    public Map<String, User> getAllUsersMap() {
        return userRepository.findAll()
                .stream()
                .collect(Collectors.toMap(User::getEmail, Function.identity()));
    }
}
