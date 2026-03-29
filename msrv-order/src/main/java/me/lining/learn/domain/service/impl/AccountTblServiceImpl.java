package me.lining.learn.domain.service.impl;

import me.lining.learn.domain.service.AccountTblService;
import me.lining.learn.domain.entity.AccountTbl;
import me.lining.learn.infrastructure.db.mapper.AccountTblMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author your_name
 * @since 2026-03-21
 */
@Service
public class AccountTblServiceImpl extends ServiceImpl<AccountTblMapper, AccountTbl> implements AccountTblService {

}
