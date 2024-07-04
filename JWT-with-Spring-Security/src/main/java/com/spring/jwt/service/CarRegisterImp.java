package com.spring.jwt.service;


import com.spring.jwt.Interfaces.ICarRegister;
import com.spring.jwt.dto.CarDto;
import com.spring.jwt.dto.FilterDto;
import com.spring.jwt.entity.Car;
import com.spring.jwt.entity.Dealer;
import com.spring.jwt.entity.Status;
import com.spring.jwt.exception.CarNotFoundException;
import com.spring.jwt.exception.DealerNotFoundException;
import com.spring.jwt.exception.PageNotFoundException;
import com.spring.jwt.repository.CarRepo;
import com.spring.jwt.repository.DealerRepository;
import com.spring.jwt.repository.PhotoRepo;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarRegisterImp implements ICarRegister {
    @Autowired
    private CarRepo carRepo;

    private static final Logger logger = LoggerFactory.getLogger(CarRegisterImp.class);


    @Autowired
    private DealerRepository dealerRepo;
    @Autowired
    private PhotoRepo photoRepo;



    @Override
    public String AddCarDetails(CarDto carDto) {
//        System.out.println(carDto.getDealer_id());
        Dealer dealer=dealerRepo.findById(carDto.getDealer_id()).orElseThrow(()->new CarNotFoundException(("Dealer Not Found For ID" +carDto.getDealer_id()),HttpStatus.NOT_FOUND));
//        System.out.println(dealer.toString());
//        List<Car> dealerCar = new ArrayList<>();


              Car car =new Car(carDto);
//              car.setDealer(dealer);
//              dealerCar=dealer.getCars();

//              dealer.setCars(dealerCar);
//              dealerRepo.save(dealer);
              carRepo.save(car);
              return "car Added";



    }
    /////////////////////////////////////////////////////////////////////
    //
    //  Method Name :  editCarDetails
    //  Description   :  Used to edit The car Profile
    //  Input         :  editCarDetails
    //  Output        :  String
    //  Date 		  :  27/06/2023
    //  Author 		  :  Geetesh Gajanan Kumbalkar
    //
    /////////////////////////////////////////////////////////////////////

    @Override
    public String editCarDetails(CarDto carDto, int id) {
        Car car = carRepo.findById(id).orElseThrow(() -> new CarNotFoundException("car not found", HttpStatus.NOT_FOUND));

        if (carDto.getAcFeature() != null) {
            car.setAcFeature(carDto.getAcFeature());
        }
        if (carDto.getMusicFeature() != null) {
            car.setMusicFeature(carDto.getMusicFeature());
        }
        if (carDto.getArea() != null) {
            car.setArea(carDto.getArea());
        }
        if (carDto.getDate() != null) {
            car.setDate(carDto.getDate());
        }
        if (carDto.getBrand() != null) {
            car.setBrand(carDto.getBrand());
        }
        if (carDto.getCarInsurance() != null) {
            car.setCarInsurance(carDto.getCarInsurance());
        }
        if (carDto.getCarStatus() != null) {
            car.setCarStatus(carDto.getCarStatus());
        }
        if (carDto.getCity() != null) {
            car.setCity(carDto.getCity());
        }
        if (carDto.getColor() != null) {
            car.setColor(carDto.getColor());
        }
        if (carDto.getDescription() != null) {
            car.setDescription(carDto.getDescription());
        }
        if (carDto.getFuelType() != null) {
            car.setFuelType(carDto.getFuelType());
        }
        if (carDto.getKmDriven() != null) {
            car.setKmDriven(carDto.getKmDriven());
        }
        if (carDto.getModel() != null) {
            car.setModel(carDto.getModel());
        }
        if (carDto.getPowerWindowFeature() != null) {
            car.setPowerWindowFeature(carDto.getPowerWindowFeature());
        }
        if (carDto.getOwnerSerial() != null) {
            car.setOwnerSerial(carDto.getOwnerSerial());
        }
        if (carDto.getPrice() != null) {
            car.setPrice(carDto.getPrice());
        }
        if (carDto.getRearParkingCameraFeature() != null) {
            car.setRearParkingCameraFeature(carDto.getRearParkingCameraFeature());
        }
        if (carDto.getRegistration() != null) {
            car.setRegistration(carDto.getRegistration());
        }
        if (carDto.getCarInsuranceDate() != null) {
            car.setCarInsuranceDate(carDto.getCarInsuranceDate());
        }
        if (carDto.getTitle() != null) {
            car.setTitle(carDto.getTitle());
        }
        if (carDto.getVariant() != null) {
            car.setVariant(carDto.getVariant());
        }
        if (carDto.getTransmission() != null) {
            car.setTransmission(carDto.getTransmission());
        }
        if (carDto.getYear() != null) {
            car.setYear(carDto.getYear());
        }

        carRepo.save(car);
        return "Car Updated " + id;
    }


    @Override
    public List<CarDto> getAllCarsWithPages(int pageNo, int pageSize) {

        List<Car> listOfCar = carRepo.getPendingAndActivateCarOrderedByIdDesc();
        if (listOfCar.isEmpty()) {
            throw new CarNotFoundException("Car not found");
        }
        System.out.println("Car IDs from repository:");
        listOfCar.forEach(car -> System.out.println("Car ID: " + car.getId()));


        int totalCars = listOfCar.size();
        int totalPages = (int) Math.ceil((double) totalCars / pageSize);

        if (pageNo < 0 || pageNo >= totalPages) {
            throw new PageNotFoundException("Page not found");
        }

        int pageStart = pageNo * pageSize;
        int pageEnd = Math.min(pageStart + pageSize, totalCars);

        List<CarDto> listOfCarDto = new ArrayList<>();
        for (int i = pageStart; i < pageEnd; i++) {
            Car car = listOfCar.get(i);
            CarDto carDto = new CarDto(car);
            carDto.setCarId(car.getId());
            listOfCarDto.add(carDto);
        }

        System.out.println("CarDto IDs after pagination:");
        listOfCarDto.forEach(carDto -> System.out.println("CarDto ID: " + carDto.getCarId()));

        return listOfCarDto;
    }


    @Override
    public String deleteCar(int carId, int dealerId) {
        Optional<Car> carOptional = carRepo.findById(carId);
        if (carOptional.isPresent()) {
            Car carDetail = carOptional.get();
            int cardealerId = carDetail.getDealerId();
            if (cardealerId == dealerId) {
                Long carDocumentPhotoId = carDetail.getCarPhotoId();
                if (carDocumentPhotoId != null && carDocumentPhotoId != 0) {
                    photoRepo.deleteById(carDocumentPhotoId);
                }
                carRepo.deleteById(carId);
                return "Car details deleted";
            } else {
                throw new RuntimeException("You are not authorized to delete this car");
            }
        } else {
            throw new CarNotFoundException("Car not found", HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public CarDto getCarById(int carId) {
        Optional<Car> car = carRepo.findById(carId);
        return car.map(CarDto::new).orElse(null);
    }

    @Override
    public List<CarDto> searchByFilter(FilterDto filterDto, int pageNo) {
        Specification<Car> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterDto.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("price"), filterDto.getMinPrice()));
            }
            if (filterDto.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("price"), filterDto.getMaxPrice()));
            }

            if (filterDto.getArea() != null && !filterDto.getArea().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("area"), filterDto.getArea()));
            }
            if (filterDto.getYear() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("year"), filterDto.getYear()));
            }
            if (filterDto.getBrand() != null && !filterDto.getBrand().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), filterDto.getBrand()));
            }
            if (filterDto.getModel() != null && !filterDto.getModel().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("model"), filterDto.getModel()));
            }
            if (filterDto.getTransmission() != null && !filterDto.getTransmission().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("transmission"), filterDto.getTransmission()));
            }
            if (filterDto.getFuelType() != null && !filterDto.getFuelType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("fuelType"), filterDto.getFuelType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(pageNo - 1, 5);

        Page<Car> carPage = carRepo.findAll(spec, pageable);
        if(carPage.isEmpty()){
            throw new PageNotFoundException("Page Not found");
        }
        List<CarDto> listOfCarDto =new ArrayList<>();

        for (int counter=0;counter<carPage.getContent().size();counter++){

            CarDto carDto = new CarDto(carPage.getContent().get(counter));
            carDto.setCarId(carPage.getContent().get(counter).getId());
            listOfCarDto.add(carDto);
        }

        return listOfCarDto;
    }

    @Override
    public CarDto findById(int carId) {
        Optional<Car> car = carRepo.findById(carId);
        if (car.isEmpty()){
            throw new CarNotFoundException("car not found",HttpStatus.NOT_FOUND);
        }
        CarDto carDto = new CarDto(car.get());
        carDto.setCarId(carId);
        return carDto;
    }

    @Override
    public List<CarDto> getDetails(int dealerId, Status carStatus, int pageNo) {
        if (!dealerExists(dealerId)) {
            throw new DealerNotFoundException("Dealer not found by id");
        }

        List<Car> listOfCar = carRepo.findByDealerIdAndCarStatus(dealerId, carStatus);

        if (listOfCar.isEmpty()) {
            throw new CarNotFoundException("Car not found", HttpStatus.NOT_FOUND);
        }

        int pageSize = 10;
        int pageStart = pageNo * pageSize;
        int pageEnd = Math.min(pageStart + pageSize, listOfCar.size());

        if (pageStart >= listOfCar.size()) {
            throw new PageNotFoundException("Page not found");
        }

        List<CarDto> listOfCarDto = new ArrayList<>();
        for (int i = pageStart; i < pageEnd; i++) {
            Car car = listOfCar.get(i);
            CarDto carDto = new CarDto(car);
            carDto.setCarId(car.getId());
            listOfCarDto.add(carDto);
        }

        return listOfCarDto;
    }

    @Override
    public Page<CarDto> getCarsByDealerId(Integer dealerId, Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Car> allProfiles = carRepo.findByDealerId(dealerId, pageable);

        if (allProfiles.isEmpty()) {
            throw new PageNotFoundException("Page Not Found");
        }

        List<CarDto> profileDtoList = allProfiles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(profileDtoList, pageable, allProfiles.getTotalElements());
    }

    private boolean dealerExists(int dealerId) {
        return dealerRepo.existsById(dealerId);
    }

    @Override
    public String editCarDetails(CarDto carDto) {
        return null;
    }

    private CarDto convertToDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setCarId(car.getId());
        carDto.setDealer_id(car.getDealerId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setYear(car.getYear());
        carDto.setArea(car.getArea());
        carDto.setCarInsurance(car.getCarInsurance());
        carDto.setCarStatus(car.getCarStatus());
        carDto.setCity(car.getCity());
        carDto.setColor(car.getColor());
        carDto.setDescription(car.getDescription());
        carDto.setFuelType(car.getFuelType());
        carDto.setKmDriven(car.getKmDriven());
        carDto.setOwnerSerial(car.getOwnerSerial());
        carDto.setPrice(car.getPrice());
        carDto.setRegistration(car.getRegistration());
        carDto.setTransmission(car.getTransmission());
        carDto.setAcFeature(car.getAcFeature());
        carDto.setMusicFeature(car.getMusicFeature());
        carDto.setPowerWindowFeature(car.getPowerWindowFeature());
        carDto.setRearParkingCameraFeature(car.getRearParkingCameraFeature());
        carDto.setCarInsuranceDate(car.getCarInsuranceDate());
        carDto.setTitle(car.getTitle());
        carDto.setVariant(car.getVariant());
        return carDto;
    }
}
