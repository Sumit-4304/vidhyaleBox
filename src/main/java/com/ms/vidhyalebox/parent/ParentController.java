package com.ms.vidhyalebox.parent;

import com.ms.shared.api.auth.ParentSignupRequestDTO;
import com.ms.shared.util.util.bl.IGenericService;
import com.ms.shared.util.util.domain.GenericEntity;
import com.ms.shared.util.util.rest.GenericController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/parent")
public class ParentController extends GenericController<ParentSignupRequestDTO, Long> {
	private final ParentServiceImpl _parentService;


	public ParentController(final ParentServiceImpl parentService
                          ) {
		_parentService = parentService;
	}

	@Override
	public IGenericService<GenericEntity, Long> getService() {
		_parentService.setAuthToken(getAuthToken());
		return _parentService;
	}

 /*   @PostMapping("/signup")
    	public ResponseEntity<ParentEntity> signup(@RequestBody ParentEntity parentEntity) {
    		ParentEntity savedUser = _parentService.signup(parentEntity);
    		return ResponseEntity.ok(savedUser);
    	}

    	@PostMapping("/login")
    	public ResponseEntity<String> login(@RequestParam String phoneNumber, @RequestParam String password) {
    		String token = _parentService.login(phoneNumber, password);
    		return ResponseEntity.ok(token);
    	}*/

    	@PostMapping("/logout")
    	public ResponseEntity<Void> logout() {
    		_parentService.logout();
    		return ResponseEntity.ok().build();
    	}
}
