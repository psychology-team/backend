package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.DiagnosticResultDTO;
import com.psychology.product.repository.model.DiagnosticResultDAO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DiagnosticResultMapperImpl implements DiagnosticResultMapper {

    @Override
    public DiagnosticResultDTO toDTO(DiagnosticResultDAO result) {
        if ( result == null ) {
            return null;
        }

        short interpretationPoints = result.getInterpretationPoints();
        short scalePoints = result.getScalePoints();

        UUID userId = result.getUserDAO().getId();
        UUID diagnosticId = result.getDiagnosticDAO().getDiagnosticId();

        return new DiagnosticResultDTO( userId, diagnosticId, interpretationPoints, scalePoints );
    }

    @Override
    public List<DiagnosticResultDTO> toDTO(List<DiagnosticResultDAO> results) {
        if ( results == null ) {
            return null;
        }

        List<DiagnosticResultDTO> list = new ArrayList<>(results.size());
        for ( DiagnosticResultDAO diagnosticResultDAO : results ) {
            list.add( toDTO( diagnosticResultDAO ) );
        }

        return list;
    }
}