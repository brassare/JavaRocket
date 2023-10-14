package br.com.viniciusfernandes.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.var;

/**
 * Modificador
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    
/**
 * String (texto)
 * Integer (int) numeros inteiros
 * Double double Numero 0.000
 * Float (float) numero 0.000
 * char ( A C)
 * Date (data)
 * Void ( não tem retorno do metodo)
 */
/*
 * body
 */
@PostMapping("/")
private ResponseEntity create(@RequestBody UserModel userModel) {
       var user = this.userRepository.findByUsername(userModel.getUsername());

       if(user != null){
            System.out.println("Usuário Já Existe na base");
            //Mensagem de erro
            //Status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário Já Existe");
       }

       var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

       userModel.setPassword(passwordHashred);

       var userCreated = this.userRepository.save(userModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
