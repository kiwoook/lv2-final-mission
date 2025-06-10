package finalmission.member.controller;

import finalmission.member.dto.request.MemberCreateRequest;
import finalmission.member.dto.response.MemberInfoResponse;
import finalmission.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberInfoResponse> signUp(@Validated @RequestBody MemberCreateRequest createRequest) {
        MemberInfoResponse response = memberService.signUp(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
