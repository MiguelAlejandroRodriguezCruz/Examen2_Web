package com.upiiz.Examen2.Controllers;

import com.upiiz.Examen2.Entities.Return;
import com.upiiz.Examen2.Dto.CustomResponse;
import com.upiiz.Examen2.Services.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1")
public class ReturnController {

    @Autowired
    private ReturnService ReturnService;

    @GetMapping("/listar")
    public ResponseEntity<CustomResponse<List<Return>>> getReturns() {
        List<Return> Returns = new ArrayList<>();
        Link allReturnsLink = linkTo(ReturnController.class).withSelfRel();
        List<Link> links = List.of(allReturnsLink);

        try {
            Returns = ReturnService.getReturns();
            if (!Returns.isEmpty()) {
                CustomResponse<List<Return>> response = new CustomResponse<>(
                        1, "Returns encontrados", Returns, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(
                        0, "Returns no encontrados", Returns, links));
            }
        } catch (Exception e) {
            CustomResponse<List<Return>> response = new CustomResponse<>(
                    0, "Error interno de servidor", Returns, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get Return by ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<CustomResponse<Return>> getReturnById(@PathVariable("id") Long id) {
        Optional<Return> Return = ReturnService.getReturnById(id);
        Link allReturnsLink = linkTo(ReturnController.class).withSelfRel();
        List<Link> links = List.of(allReturnsLink);

        if (Return.isPresent()) {
            CustomResponse<Return> response = new CustomResponse<>(1, "Return encontrado", Return.get(),
                    links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            CustomResponse<Return> response = new CustomResponse<>(0, "Return no encontrado", null, links);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Create Return
    @GetMapping("/crear")
    public ResponseEntity<CustomResponse<Return>> createReturn(@RequestBody Return Return) {
        Link allReturnsLink = linkTo(ReturnController.class).withSelfRel();
        List<Link> links = List.of(allReturnsLink);

        try {
            Return createdReturn = ReturnService.createReturn(Return);
            CustomResponse<Return> response = new CustomResponse<>(1, "Return creado", createdReturn,
                    links);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            CustomResponse<Return> response = new CustomResponse<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Update Return
    @GetMapping("/actualizar/{id}")
    public ResponseEntity<CustomResponse<Return>> updateReturn(@RequestBody Return Return,
            @PathVariable Long id) {
        Link allReturnsLink = linkTo(ReturnController.class).withSelfRel();
        List<Link> links = List.of(allReturnsLink);

        Return.setId(id);
        if (ReturnService.getReturnById(id).isPresent()) {
            Return updatedReturn = ReturnService.updateReturn(Return);
            CustomResponse<Return> response = new CustomResponse<>(1, "Return actualizado",
                    updatedReturn, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            CustomResponse<Return> response = new CustomResponse<>(0, "Return no encontrado", null, links);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Delete Return by ID
    @GetMapping("/eliminar/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteReturnById(@PathVariable Long id) {
        Link allReturnsLink = linkTo(ReturnController.class).withSelfRel();
        List<Link> links = List.of(allReturnsLink);

        if (ReturnService.getReturnById(id).isPresent()) {
            ReturnService.deleteReturnById(id);
            CustomResponse<Void> response = new CustomResponse<>(1, "Return eliminado", null, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            CustomResponse<Void> response = new CustomResponse<>(0, "Return no encontrado", null, links);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
