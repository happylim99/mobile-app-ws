package com.sean.ws.service;

import com.sean.ws.shared.dto.UserDto;
import com.sean.ws.ui.model.request.EmailRequestModel;

public interface EmailService {
String sendEmail(EmailRequestModel emailRequestModel);
void sendEmail2(EmailRequestModel emailRequestModel);
void verifyEmail(UserDto userDto);

}
