UPDATE core_configuration set conf_key = 'spring.emailService.findAll.subject.id.forgotemail'
  WHERE conf_key = 'spring.emailTemplateDao.findAll.subject.id.forgotemail';
UPDATE core_configuration set conf_key = 'spring.emailService.findAll.subject.id.reconfirmemail'
  WHERE conf_key = 'spring.emailTemplateDao.findAll.subject.id.reconfirmemail';
UPDATE core_configuration set conf_key = 'spring.emailService.findAll.subject.id.contactformemail'
  WHERE conf_key = 'spring.emailTemplateDao.findAll.subject.id.contactformemail';
UPDATE core_configuration set conf_key = 'spring.emailService.findAll.subject.id.regemail'
  WHERE conf_key = 'spring.emailTemplateDao.findAll.subject.id.regemail';
UPDATE core_configuration set conf_key = 'spring.emailService.findAll.subject.id.registereduser'
  WHERE conf_key = 'spring.emailTemplateDao.findAll.subject.id.registereduser';
UPDATE core_configuration set conf_key = 'spring.emailService.findAll.subject.id.unknownerror'
  WHERE conf_key = 'spring.emailTemplateDao.findAll.subject.id.unknownerror';
UPDATE core_configuration set conf_key = 'spring.emailService.findAll.subject.id.forgotemail'
  WHERE conf_key = 'spring.emailTemplateDao.findAll.subject.id.forgotemail';
UPDATE core_configuration set conf_key = 'spring.roleService.findAll.description.id.guestrole'
  WHERE conf_key = 'spring.roleDao.findAll.description.id.guestrole';
UPDATE core_configuration set conf_key = 'spring.roleService.findAll.description.id.registerrole'
  WHERE conf_key = 'spring.roleDao.findAll.description.id.registerrole';

-- TODO migrate oracle rights
-- TODO migration mount points
-- TODO custom style