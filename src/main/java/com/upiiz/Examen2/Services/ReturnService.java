package com.upiiz.Examen2.Services;

import com.upiiz.Examen2.Entities.Return;
import com.upiiz.Examen2.Repositories.ReturnRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReturnService {

    @Autowired
    private ReturnRepository ReturnRepository;

    public List<Return> getReturns() {
        return ReturnRepository.findAll();
    }

    public Optional<Return> getReturnById(Long id) {
        return ReturnRepository.findById(id);
    }

    public Return createReturn(Return Return) {
        return ReturnRepository.save(Return);
    }

    public Return updateReturn(Return Return) {
        return ReturnRepository.save(Return);
    }

    public void deleteReturnById(Long id) {
        ReturnRepository.deleteById(id);
    }
}
