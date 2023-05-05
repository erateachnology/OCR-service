package io.marketplace.services.ocr.implementation.api.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import io.marketplace.commons.model.dto.ListResponseDto;;
import io.marketplace.services.ocr.implementation.service.OCRImplementationService;

public class OCRImplementationDelegate implements SampleDataApiDelegate {

	@Autowired
	private OCRImplementationService OCRImplementationService;

	private ResponseEntity<ServiceDataResponse> asBody(ListResponseDto<String> listDto) {
		ServiceDataResponse response = new ServiceDataResponse();
		response.data(listDto.getData());

		ResponseStatus status = new ResponseStatus();
		status.code("000000");
		status.message("Success");
		response.setStatus(status);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ServiceDataResponse> getExampleData() {
		return asBody(OCRImplementationService.getListOfHelloWorldText("", 0, 0));
	}

}


