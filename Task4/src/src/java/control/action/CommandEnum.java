package src.java.control.action;

public enum CommandEnum {
	REGISTRATIONSAVE {
		{
			this.command = new RegistrationSaveCommand();
		}
	},
	REGISTRATION {
		{
			this.command = new RegistrationCommand();
		}
	},	
	MANAGERPROJECTSAVE {
		{
			this.command = new ManagerProjectSaveCommand();
		}
	},	
	MANAGERPROJECTCREATE {
		{
			this.command = new ManagerProjectCreateCommand();
		}
	},	
	MANAGERPROJECTLIST {
		{
			this.command = new ManagerProjectListCommand();
		}
	},
	CUSTOMERTECHNICALTASKCREATE {
		{
			this.command = new CustomerTechnicalTaskCreateCommand();
		}
	},
	CUSTOMERTECHNICALTASKSAVE {
		{
			this.command = new CustomerTechnicalTaskSaveCommand();
		}
	},
	CUSTOMERTECHNICALTASKLIST {
		{
			this.command = new CustomerTechnicalTaskListCommand();
		}
	},
	SAVEPROGRAMMERSTATISTICS {
		{
			this.command = new SaveProgrammerStatisticsCommand();
		}
	},
	PROGRAMMERSTATISTICS {
		{
			this.command = new ProgrammerStatisticsCommand();
		}
	},
	LOGIN {
		{
			this.command = new LoginCommand();
		}
	},
	LOGOUT {
		{
			this.command = new LogoutCommand();	
		}
	};
	ActionCommand command;
	public ActionCommand getCurrentCommand() {
		return command;
	}

}
