package com.soez.mtc.report.Controller;

import com.soez.mtc.report.dto.ReportRegistDto;
import com.soez.mtc.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@Api(tags={"신고관련 컨트롤러"})
public class ReportController {
    @Autowired
    private ReportService reportService;

    //****************************************** CREATE ****************************************
    @ApiOperation(value = "신고 생성", notes = "신고를 생성.'success' 또는 'fail' 문자열을 반환한다. 게시글인지 댓글인지 덧글인지 알려줘야한다 프론트에서", response = String.class)
    @PostMapping("/create")
    public ResponseEntity<String> createReport(@RequestBody @ApiParam(value = "신고 정보 게시글인지 댓글인지 답글인지 알려줘야해", required = true) ReportRegistDto reportRegistDto) throws Exception {
        if(reportService.createReport(reportRegistDto)!=null){
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
        }
    }
}
