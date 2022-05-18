import com.boos.stock.data.local.CompanyListingEntity
import com.boos.stock.data.remote.dto.CompanyInfoDto
import com.boos.stock.domain.model.CompanyInfoModel
import com.boos.stock.domain.model.CompanyListingModel

fun CompanyListingModel.toCompanyListingEntity(): CompanyListingEntity =
    CompanyListingEntity(
        symbol = symbol,
        exchange = exchange,
        name = name,
        isFavorite = isFavorite
    )

fun CompanyListingEntity.toCompanyListing(): CompanyListingModel =
    CompanyListingModel(
        symbol = symbol,
        exchange = exchange,
        name = name,
        isFavorite = isFavorite
    )

fun CompanyInfoDto.toCompanyInfo(): CompanyInfoModel =
    CompanyInfoModel(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )