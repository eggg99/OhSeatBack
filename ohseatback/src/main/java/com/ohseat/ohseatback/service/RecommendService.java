package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.dto.JoinRequest;
import com.ohseat.ohseatback.dto.PasswordChangeRequest;
import com.ohseat.ohseatback.dto.UserUpdateRequest;
import com.ohseat.ohseatback.exception.business.DuplicateResourceException;
import com.ohseat.ohseatback.exception.business.NoChangesDetectedException;
import com.ohseat.ohseatback.exception.business.UserNotFoundException;
import com.ohseat.ohseatback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecommendService {


}
