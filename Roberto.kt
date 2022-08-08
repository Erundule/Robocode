package Roberto

import dev.robocode.tankroyale.botapi.Bot
import dev.robocode.tankroyale.botapi.BotInfo
import dev.robocode.tankroyale.botapi.Color
import dev.robocode.tankroyale.botapi.events.*
import java.net.URI


class Roberto: Bot(BotInfo.fromFile("res/Roberto.json"), URI.create("ws://localhost:7654"),"f2UkT1faAybpwyJStLq/1Q") {
    override fun run() {
        bodyColor = Color.PURPLE
        gunColor = Color.GREEN
        radarColor = Color.ORANGE
        turretColor = Color.ORANGE
        bulletColor = Color.FUCHSIA
        scanColor = Color.BLUE

        while (this.isRunning) {
            forward(100.0)
            turnGunRight(180.0)
            back(75.0)
            turnGunRight(180.0)
            escapeMovement()

        }
    }
    override fun onScannedBot(event: ScannedBotEvent) {
        stop()
        aim()
        val enemyDistance = distanceTo(event.x, event.y)
        if (enemyDistance > 200.0 || this.energy < 15.0) {
            fire(1.0)
        } else if (enemyDistance > 50.0) {
            fire(2.0)
        } else if (event.energy < 20.0 && enemyDistance < 50.0){
            fire(3.0)
        }
        resume()
    }
    private fun escapeMovement(){
        turnRight(45.0)
        forward(250.0)
        turnRight(45.0)
    }
    private fun aim(){
        val gunBearing = gunBearingTo(x, y)
        setTurnGunRight(gunBearing)
    }

    override fun onHitWall(event: HitWallEvent) {
        back(20.0)
    }

    override fun onHitBot(event: HitBotEvent) {
        stop()
        aim()
        fire(2.0)
        resume()
    }

    override fun onHitByBullet(event: BulletHitBotEvent) {
        escapeMovement()
    }
    override fun onBulletHitWall(event: BulletHitWallEvent) {
        turnGunRight(90.0)
        turnGunRight(230.0)
        rescan()
    }

}
fun main(args: Array<String>){
    Roberto().start()
}


