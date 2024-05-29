package katecam.racingcar.application.service.query;


import java.util.List;
import katecam.racingcar.application.dto.query.CarPositionRes;
import katecam.racingcar.application.dto.query.GameTotalResultRes;
import katecam.racingcar.application.dto.query.GameTurnResultRes;
import katecam.racingcar.application.port.in.query.GameStatusQuery;
import katecam.racingcar.application.port.out.GameRepository;
import katecam.racingcar.domain.Car;
import katecam.racingcar.domain.Game;

//TODO: 매번 게임 가져오는거 좀 그런데
public class GameStatusQueryService implements GameStatusQuery {
    private final GameRepository gameRepository;

    public GameStatusQueryService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public boolean isEnded() {
        Game game = gameRepository.getOrThrow();
        return game.isEnded();
    }

    @Override
    public GameTurnResultRes getTurnResult() {
        Game game = gameRepository.getOrThrow();
        List<CarPositionRes> carPositions= game.getCars().stream()
                 .map(car->new CarPositionRes(car.getName(), car.getPosition()))
                 .toList();
        return new GameTurnResultRes(carPositions);
    }

    @Override
    public GameTotalResultRes getTotalResult() {
        Game game = gameRepository.getOrThrow();
        List<String> winnerNames = game.getWinners().stream()
                .map(Car::getName)
                .toList();
        return new GameTotalResultRes(winnerNames);
    }
}
