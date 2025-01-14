package com.ourMenu.backend.domain.test.api;

import com.ourMenu.backend.domain.test.api.request.SaveEntityRequest;
import com.ourMenu.backend.domain.test.api.request.SaveJdbcRequest;
import com.ourMenu.backend.domain.test.api.response.FindEntityByIdResponse;
import com.ourMenu.backend.domain.test.api.response.FindJdbcByIdResponse;
import com.ourMenu.backend.domain.test.api.response.SaveEntityResponse;
import com.ourMenu.backend.domain.test.application.TestService;
import com.ourMenu.backend.domain.test.domain.JdbcEntity;
import com.ourMenu.backend.domain.test.domain.TestEntity;
import com.ourMenu.backend.global.common.ApiResponse;
import com.ourMenu.backend.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/jpa/save")
    @Operation(summary = "테스트회원 저장", description = "swagger 테스트를 위한 저장 API")
    public ApiResponse<SaveEntityResponse> saveTestEntity(@RequestBody SaveEntityRequest saveEntityRequest) throws IOException {
        TestEntity testEntity=TestEntity
                .builder()
                .name(saveEntityRequest.name())
                .build();

        TestEntity saveTestEntity = testService.saveTestEntity(testEntity);

        SaveEntityResponse saveEntityResponse = SaveEntityResponse
                .builder()
                .id(saveTestEntity.getId())
                .name(saveEntityRequest.name())
                .build();
        return ApiUtils.success(saveEntityResponse);
    }

    @GetMapping("/jpa/{SaveEntityId}")
    @Operation(summary = "테스트회원 조회", description = "swagger 테스트를 위한 조회 API")
    public ApiResponse<FindEntityByIdResponse> findById(@PathVariable("SaveEntityId")Long saveEntityId){
        TestEntity findTestEntity = testService.findTestEntity(saveEntityId);
        FindEntityByIdResponse findEntityByIdResponse = FindEntityByIdResponse
                .builder()
                .id(findTestEntity.getId())
                .name(findTestEntity.getName())
                .build();
        return ApiUtils.success(findEntityByIdResponse);
    }

    @PostMapping("/jdbc/save")
    public void save(@RequestBody @Valid SaveJdbcRequest saveJdbcRequest){
        testService.saveJdbcEntity(saveJdbcRequest.name());
    }

    @GetMapping("/jdbc/{SaveEntityId}")
    public ApiResponse<FindJdbcByIdResponse> findJdbcEntityById(@PathVariable("SaveEntityId")Long saveEntityId){
        JdbcEntity jdbcEntity = testService.findJdbcEntity(saveEntityId);
        FindJdbcByIdResponse findJdbcByIdResponse = FindJdbcByIdResponse
                .builder()
                .id(jdbcEntity.getId())
                .name(jdbcEntity.getName())
                .build();
        return ApiUtils.success(findJdbcByIdResponse);
    }
}
