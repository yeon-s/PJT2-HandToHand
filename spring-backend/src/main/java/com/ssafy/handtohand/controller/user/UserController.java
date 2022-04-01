package com.ssafy.handtohand.controller.user;

import com.ssafy.handtohand.domain.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 관련 컨트롤러
 *
 * @author Eunee Chung
 * created on 2022-03-30
 */

@Api(tags = "회원 관련 기능")
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "회원 추가")
    public ResponseEntity<String> addUser(@ApiParam(value = "회원 지갑 주소") @RequestHeader("wallet-address") String address) {
        return new ResponseEntity<>(userService.insertUser(address), HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation(value = "닉네임 수정")
    public ResponseEntity<String> updateNickName(@ApiParam(value = "닉네임") @RequestHeader("nickname") String nickname, @ApiParam(value = "회원 지갑 주소") @RequestHeader("wallet-address") String address) {
        return new ResponseEntity<>(userService.updateNickName(nickname, address), HttpStatus.OK);
    }
}
