package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.UserService;
import com.spring.jwt.dto.RegisterDto;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.exception.UnauthorizedException;
import com.spring.jwt.exception.UserAlreadyExistException;
import com.spring.jwt.utils.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> register(@RequestBody @Valid RegisterDto registerDto) {
        try {
            BaseResponseDTO response = userService.registerAccount(registerDto);
            return ResponseEntity.ok().body(new BaseResponseDTO("Successful", response.getMessage()));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(new BaseResponseDTO("Unsuccessful", "User already exists"));
        } catch (BaseException e) {
            return ResponseEntity.badRequest().body(new BaseResponseDTO("Unsuccessful", "Invalid role"));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponseDTO("Unsuccessful", e.getMessage()));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponseDTO("Unsuccessful", e.getMessage()));
        }
    }

    private String processFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }
}
