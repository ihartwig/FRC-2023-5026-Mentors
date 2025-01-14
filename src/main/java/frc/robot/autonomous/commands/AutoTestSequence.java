package frc.robot.autonomous.commands;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.FollowTrajectoryCommand;
import frc.robot.subsystems.DrivebaseSubsystem;

public class AutoTestSequence extends SequentialCommandGroup {
  public AutoTestSequence(
      double maxVelocityMetersPerSecond,
      double maxAccelerationMetersPerSecondSq,
      SwerveDriveKinematics kinematics,
      DrivebaseSubsystem drivebaseSubsystem) {

    PathPlannerTrajectory path =
        PathPlanner.loadPath(
            "auto test", maxAccelerationMetersPerSecondSq, maxAccelerationMetersPerSecondSq);

    addCommands(new FollowTrajectoryCommand(path, true, drivebaseSubsystem));
  }
}
