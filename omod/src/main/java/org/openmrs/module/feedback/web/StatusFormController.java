/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */



package org.openmrs.module.feedback.web;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.api.context.Context;
import org.openmrs.module.feedback.FeedbackService;
import org.openmrs.module.feedback.Status;
import org.openmrs.web.WebConstants;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.SimpleFormController;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class StatusFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    protected String formBackingObject(HttpServletRequest request) throws Exception {
        Object          o                = Context.getService(FeedbackService.class);
        FeedbackService service          = (FeedbackService) o;
        String          feedbackStatusId = request.getParameter("feedbackStatusId");
        String          text             = "Not Used";
        String          status           = request.getParameter("status");

        if (!StringUtils.hasLength(feedbackStatusId)
                || (service.getStatus(Integer.parseInt(feedbackStatusId)) == null)) {    /* Just for the statistics */
            request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "feedback.notification.status.deleted");
            text = feedbackStatusId;
        }

        /* Delete the data incase delete has been selected by the user */
        else if ((feedbackStatusId != null) && "1".equals(request.getParameter("delete"))) {
            Status s = service.getStatus(Integer.parseInt(feedbackStatusId));

            service.deleteStatus(s);
            request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "feedback.notification.status.deleted");
            text = feedbackStatusId;
        }

        /* Saves the data incase save has been selected by the user */
        else if ((feedbackStatusId != null) && "1".equals(request.getParameter("save"))) {
            Status s = service.getStatus(Integer.parseInt(feedbackStatusId));

            s.setStatus(status);
            service.saveStatus(s);
            request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "feedback.notification.status.saved");
            text = feedbackStatusId;
        }

        log.debug("Returning hello world text: " + text);

        return text;
    }

    @Override
    protected Map referenceData(HttpServletRequest req) throws Exception {
        Map<String, Object> map              = new HashMap<String, Object>();
        Object              o                = Context.getService(FeedbackService.class);
        FeedbackService     service          = (FeedbackService) o;
        FeedbackService     hService         = (FeedbackService) Context.getService(FeedbackService.class);
        String              feedbackStatusId = req.getParameter("feedbackStatusId");

        /* Tells wheather the data is deleted or not */
        if (!StringUtils.hasLength(feedbackStatusId)
                || (service.getStatus(Integer.parseInt(feedbackStatusId)) == null)) {
            Status s = new Status();

            map.put("statuses", s);
            req.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "feedback.notification.status.deleted");

            return map;
        }

        /* Otherwise return the data based on the input */
        else {
            Status s = service.getStatus(Integer.parseInt(feedbackStatusId));

            System.out.println(s.getfeedbackStatusId());
            map.put("statuses", s);

            return map;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
