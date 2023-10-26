package zw.org.nmr.limsehr.service;

import zw.org.nmr.limsehr.domain.Client;
import zw.org.nmr.limsehr.service.dto.DashboardSummaryDTO;

/**
 * Service Interface for managing {@link Client}.
 */
public interface DashboardService {
    DashboardSummaryDTO countSummary();
}
