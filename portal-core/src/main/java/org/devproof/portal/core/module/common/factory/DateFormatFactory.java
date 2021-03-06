/*
 * Copyright 2009-2011 Carsten Hufe devproof.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devproof.portal.core.module.common.factory;

import java.text.SimpleDateFormat;

/**
 * DateFormatFactory provides application scoped DateFormaters Considers the
 * user session locale
 *
 * @author Carsten Hufe
 */
public interface DateFormatFactory {
    /**
     * Creates an instance of date formater without time for displaying
     */
    SimpleDateFormat createDisplayDateFormat();

    /**
     * Creates an instance of date formater with time for displaying
     */
    SimpleDateFormat createDisplayDateTimeFormat();

    /**
     * Creates an instance of date formater without time for input
     */
    SimpleDateFormat createInputDateFormat();

    /**
     * Creates an instance of date formater with time for input
     */
    SimpleDateFormat createInputDateTimeFormat();
}
